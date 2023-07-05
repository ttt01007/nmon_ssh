package core;

import base.BasicInfo;
import com.trilead.ssh2.SCPClient;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Download extends Core implements CoreService {
    static Logger logger = Logger.getLogger(Download.class);

    public Download(BasicInfo basicInfo, ServerInfo serverInfo) {
        super(basicInfo, serverInfo);
    }

    @Override
    public void Run() throws IOException {
        logger.info("开始下载 nmon结果");
        Init();

        logger.info("查询nmon结果文件名");
        ExecCmdReturnInfo execCmdReturnInfo = SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + "/ &&" + this.serverInfo.getServerTypeInfo().GetConfToString("ls"));

        if ((execCmdReturnInfo.GetOutInfoString().indexOf(".nmon") != -1) && (this.basicInfo.GetConfToString("tmpResultHome") == null)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
            this.basicInfo.AddConf("tmpResultHome", this.basicInfo.GetConfToString("ResultHome") + sdf.format(new Date()));
            newFolder(this.basicInfo.GetConfToString("tmpResultHome"));
        }

        SCPClient client = new SCPClient(this.conSSH);

        for (String filename : execCmdReturnInfo.GetOutInfoArrayList()) {
            if (filename.indexOf(".nmon") == -1) {
                continue;
            }
            logger.debug("按照文件名下载nmon结果");
            client.get(this.serverInfo.GetConfToString("NmonRoot") + "/" + filename, this.basicInfo.GetConfToString("tmpResultHome"));

            File f = new File(this.basicInfo.GetConfToString("tmpResultHome") + "/" + filename);
            File newFilename = new File(this.basicInfo.GetConfToString("tmpResultHome") + "/" + this.serverInfo.GetConfToString("IP") + "_" + filename);
            f.renameTo(newFilename);
            logger.info("下载到本地的nmon结果文件名为： " + this.serverInfo.GetConfToString("IP") + "_" + filename);
        }
        logger.info("删除服务器上的nmon结果文件");
        SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + "/ && " + this.serverInfo.getServerTypeInfo().GetConfToString("rmNmon") + " ");

        End();
        logger.info("nmon结果下载完成。");
    }

    private void newFolder(String folderPath) {
        File file = new File(folderPath);
        try {
            if (file.isDirectory()) {
                logger.warn("the directory is exists");
            }
            file.mkdir();
            logger.info("新建目录成功" + folderPath);
        } catch (Exception e) {
            logger.error("新建目录操作出错");
            e.printStackTrace();
        }
    }
}
