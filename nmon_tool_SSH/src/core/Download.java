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
        logger.info("��ʼ���� nmon���");
        Init();

        logger.info("��ѯnmon����ļ���");
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
            logger.debug("�����ļ�������nmon���");
            client.get(this.serverInfo.GetConfToString("NmonRoot") + "/" + filename, this.basicInfo.GetConfToString("tmpResultHome"));

            File f = new File(this.basicInfo.GetConfToString("tmpResultHome") + "/" + filename);
            File newFilename = new File(this.basicInfo.GetConfToString("tmpResultHome") + "/" + this.serverInfo.GetConfToString("IP") + "_" + filename);
            f.renameTo(newFilename);
            logger.info("���ص����ص�nmon����ļ���Ϊ�� " + this.serverInfo.GetConfToString("IP") + "_" + filename);
        }
        logger.info("ɾ���������ϵ�nmon����ļ�");
        SignExecCmd(this.serverInfo.getServerTypeInfo().GetConfToString("cd") + " " + this.serverInfo.GetConfToString("NmonRoot") + "/ && " + this.serverInfo.getServerTypeInfo().GetConfToString("rmNmon") + " ");

        End();
        logger.info("nmon���������ɡ�");
    }

    private void newFolder(String folderPath) {
        File file = new File(folderPath);
        try {
            if (file.isDirectory()) {
                logger.warn("the directory is exists");
            }
            file.mkdir();
            logger.info("�½�Ŀ¼�ɹ�" + folderPath);
        } catch (Exception e) {
            logger.error("�½�Ŀ¼��������");
            e.printStackTrace();
        }
    }
}
