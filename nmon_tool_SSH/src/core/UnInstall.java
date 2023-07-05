package core;

import base.BasicInfo;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.IOException;

public class UnInstall extends Core implements CoreService {
    static Logger logger = Logger.getLogger(UnInstall.class);

    public UnInstall(BasicInfo basicInfo, ServerInfo serverInfo) {
        super(basicInfo, serverInfo);
    }

    @Override
    public void Run() throws IOException {
        logger.info("开始卸载 nmon .......");
        Init();

        logger.info("卸载nmon程序");
        SignExecCmd("rm -rf " + this.serverInfo.GetConfToString("NmonRoot"));

        End();
        logger.info("nmon卸载完成");
    }
}
