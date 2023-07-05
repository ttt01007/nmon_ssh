import base.BasicInfo;
import core.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import service.ServerInfo;
import service.ServerTypeInfo;
import util.LoadConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class NmonTools {
    static Logger logger = Logger.getLogger(NmonTools.class);
    static String START = "START";
    static String STOP = "STOP";
    static String DOWNLOAD = "DOWNLOAD";
    static String SETUP = "SETUP";
    static String UNINSTALL = "UNINSTALL";

    public static void main(String[] args) throws IOException, InterruptedException {
        BasicConfigurator.configure();
        PropertyConfigurator.configure("./log4j.properties");
        if (args.length != 1) {
            logger.error(getHelpInfo());
            System.exit(0);
        }
        try {
            String com = args[0].toUpperCase();
            LoadConfig loadConfig = new LoadConfig("./config.xml");

            BasicInfo basicInfo = loadConfig.GetBasicInfo();
            HashMap<String, ServerTypeInfo> serverTypeInfos = loadConfig.GetServerTypeInfo();
            ArrayList<ServerInfo> servers = loadConfig.GetServerConf(serverTypeInfos);

            for (ServerInfo serverInfo : servers) {
                logger.info("=========================================");
                logger.info("ip: " + serverInfo.GetConfToString("IP"));
                logger.info("��������: " + serverInfo.GetConfToString("OSType"));
                logger.info("�û���: " + serverInfo.GetConfToString("UserName"));
                logger.info("����: " + serverInfo.GetConfToString("Password"));
                logger.info("ssh�˿ں�: " + serverInfo.GetConfToString("Port"));
                logger.info("nmon��װĿ¼: " + serverInfo.GetConfToString("NmonRoot"));
                logger.info("==========================================");

                if (START.equals(com)) {
                    new Start(basicInfo, serverInfo).Run();
                } else if (STOP.equals(com)) {
                    new Stop(basicInfo, serverInfo).Run();
                } else if (DOWNLOAD.equals(com)) {
                    new Download(basicInfo, serverInfo).Run();
                } else if (SETUP.equals(com)) {
                    new Setup(basicInfo, serverInfo).Run();
                } else if (UNINSTALL.equals(com)) {
                    new UnInstall(basicInfo, serverInfo).Run();
                } else {
                    logger.error(getHelpInfo());
                }
            }


        } catch (Exception e) {
            logger.error("δ֪����" + e.getMessage());
            e.printStackTrace();
        }


//        SimpleDateFormat sdfIn;
//        SimpleDateFormat sdfOut;
//        if (args.length == 2) {
//            sdfIn = new SimpleDateFormat("yyMMddHHmm");
//            sdfOut = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        }
//        try {
//            Date runTime = sdfIn.parse(args[1]);
//            logger.info("ϵͳ���� " + sdfOut.format(runTime) + " ���У�" + args[0]);
//            Date now = new Date();
//            if (runTime.before(now)) {
//                break label192;
//            }
//            logger.info("���� " + ((runTime.getTime() - now.getTime()) / 1000L / 60L + 1L) + "�������У� " + args[0]);
//            label192: Thread.sleep(60000L);
//        } catch (Exception e) {
//            logger.error("��ʼ����ʱ�䣬�������� \\\r\\\n    ���磺 run start 1007091430:��ָ��ʱ������nmon��2010��7��9��14��30�֣�");
//            logger.error("������Ϣ��" + e.getMessage());
//            System.exit(0);
//        }

    }

    private static String getHelpInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("�����ʽ����ȷ��\r\n\r\n");
        stringBuffer.append("���磺 \r\n");
        stringBuffer.append("run setup : ��װnmon�ļ�\r\n");
        stringBuffer.append("run start : ����nmon�ļ�\n");
        stringBuffer.append("run stop : ֹͣnmon\n");
        stringBuffer.append("run download : ����nmon���\n");
        stringBuffer.append("run Uninstall : ж��nmon\n");
//        stringBuffer.append("run start 1007091430 : ��ָ��ʱ������nmon��2010��7��9��14��30�֣� ");

        return stringBuffer.toString();
    }
}
