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
        logger.info("停止 nmon .......");
        Init();

        logger.info("执行ps，查找所有nmon进程");
        ExecCmdReturnInfo execCmdReturnInfo = SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("psNmon"));

        logger.info("执行Kill命令，关闭nmon进程");
        for (String process : execCmdReturnInfo.GetOutInfoArrayList()) {
            SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("killNmon") + " " + process);
        }
        End();
        logger.info("nmon成功停止");
    }
}
