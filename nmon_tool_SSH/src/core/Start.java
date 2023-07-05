package core;

import base.BasicInfo;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.IOException;

public class Start extends Core implements CoreService {
    static Logger logger = Logger.getLogger(Start.class);
    public Start(BasicInfo basicInfo, ServerInfo serverInfo) {
        super(basicInfo, serverInfo);
    }

    @Override
    public void Run() throws IOException {
        logger.info("开始运行 nmon .......");
        Init();

        logger.info("执行nmon程序");
        NoSignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + "/ && " + this.serverInfo.GetConfToString("NmonRoot") + "/" + this.serverInfo.getServerTypeInfo().GetConfToString("NmonFileName") + " " + this.basicInfo.GetConfToString("NmonArgs"));

        End();
        logger.info("nmon运行成功");

    }

}
