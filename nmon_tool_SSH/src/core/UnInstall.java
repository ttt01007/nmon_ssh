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
        logger.info("��ʼж�� nmon .......");
        Init();

        logger.info("ж��nmon����");
        SignExecCmd("rm -rf " + this.serverInfo.GetConfToString("NmonRoot"));

        End();
        logger.info("nmonж�����");
    }
}
