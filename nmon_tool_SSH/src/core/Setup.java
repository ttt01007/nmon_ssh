package core;

import base.BasicInfo;
import com.trilead.ssh2.SCPClient;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.IOException;

public class Setup extends Core implements CoreService {
    static Logger logger = Logger.getLogger(Setup.class);

    public Setup(BasicInfo basicInfo, ServerInfo serverInfo) {
        super(basicInfo, serverInfo);
    }

    @Override
    public void Run() throws IOException {
        logger.info("开始安装 nmon......");
        Init();

        UploadFile();

        End();
        logger.info("nmon安装 完成");

    }

    private void UploadFile() throws IOException {
        logger.info("建立 nmon 安装目录文件夹");
        logger.info("mkdir---" + this.serverInfo.getServerTypeInfo().GetConfToString("mkdir"));
        logger.info("NmonRoot---" + this.serverInfo.GetConfToString("NmonRoot"));
        logger.info("cd---" + this.serverInfo.getServerTypeInfo().GetConfToString("cd"));
        logger.info("NmonRoot---" + this.serverInfo.GetConfToString("NmonRoot"));
        SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("mkdir") + " " + this.serverInfo.GetConfToString("NmonRoot") + " && " + this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + "//");

        logger.info("上传nmon文件");
        SCPClient client = new SCPClient(this.conSSH);
        client.put(this.basicInfo.GetConfToString("NmonHome") + this.serverInfo.getServerTypeInfo().GetConfToString("NmonFileName"), this.serverInfo.GetConfToString("NmonRoot") + "/");

        logger.info("更改 nmon 文件夹及下属文件权限");
        SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + " && " + this.serverInfo.getServerTypeInfo().GetConfToString("chmod") + " " + this.serverInfo.GetConfToString("NmonRoot"));
    }


}
