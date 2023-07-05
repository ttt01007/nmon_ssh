package core;

import base.BasicInfo;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.IOException;

public class Stop extends Core implements CoreService {
    static Logger logger = Logger.getLogger(Stop.class);

    public Stop(BasicInfo basicInfo, ServerInfo serverInfo) {
        super(basicInfo, serverInfo);
    }

    @Override
    public void Run() throws IOException {
        logger.info("ֹͣ nmon .......");
        Init();

        logger.info("ִ��ps����������nmon����");
        ExecCmdReturnInfo execCmdReturnInfo = SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("psNmon"));

        logger.info("ִ��Kill����ر�nmon����");
        for (String process : execCmdReturnInfo.GetOutInfoArrayList()) {
            SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("killNmon") + " " + process);
        }
        End();
        logger.info("nmon�ɹ�ֹͣ");
    }
}
