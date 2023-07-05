package core;

import base.BasicInfo;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
import org.apache.log4j.Logger;
import service.ServerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Core {
    static final Logger logger = Logger.getLogger(Core.class);
    BasicInfo basicInfo;
    ServerInfo serverInfo;
    Connection conSSH;

    public Core(BasicInfo basicInfo, ServerInfo serverInfo) {
        this.basicInfo = basicInfo;
        this.serverInfo = serverInfo;
    }

    public void Init() throws IOException {
        this.conSSH = new Connection(this.serverInfo.GetConfToString("IP"), this.serverInfo.GetConfToInt("Port"));
        this.conSSH.connect();
        logger.debug("连接 SSH 服务器，成功");

        boolean isAuthenticated = this.conSSH.authenticateWithPassword(this.serverInfo.GetConfToString("UserName"), this.serverInfo.GetConfToString("Password"));
        if (!(isAuthenticated)) {
            throw new IOException("用户名密码验证失败");
        }
        logger.debug("SSH 服务器认证，成功");
    }

    public void End() {
        this.conSSH.close();
        logger.debug("关闭 SSH 服务器，成功");
    }

    public ExecCmdReturnInfo SignExecCmd(String cmd) throws IOException {
        return execCmd(cmd, true);
    }

    public ExecCmdReturnInfo NoSignExecCmd(String cmd) throws IOException {
        return execCmd(cmd, false);
    }

    private ExecCmdReturnInfo execCmd(String cmd, boolean isReturnInfo) throws IOException {
        logger.debug("execCommad : " + cmd);

        ExecCmdReturnInfo execCmdReturnInfo = new ExecCmdReturnInfo();

        Session sess = this.conSSH.openSession();
        sess.execCommand("LANG=\"us\" &&" + cmd);

        if (isReturnInfo) {
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader err = new BufferedReader(new InputStreamReader(stderr));
            while (true) {
                String line = err.readLine();
                if (line == null) {
                    break;
                }
                execCmdReturnInfo.AddErrInfo(line);
            }

            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader out = new BufferedReader(new InputStreamReader(stdout));
            while (true) {
                String line = out.readLine();
                if (line == null) {
                    break;
                }
                execCmdReturnInfo.AddOutInfo(line);
            }

            while (true) {
                if (sess.getExitStatus() != null) {
                    execCmdReturnInfo.setExitStatus(sess.getExitStatus().intValue());
                    break;
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException loaclInterruptedException) {

                }
            }

        }
        sess.close();
        logger.debug("服务器返回信息： " + execCmdReturnInfo.toString());
        return execCmdReturnInfo;
    }
}
