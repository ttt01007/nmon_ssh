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
                logger.info("操作类型: " + serverInfo.GetConfToString("OSType"));
                logger.info("用户名: " + serverInfo.GetConfToString("UserName"));
                logger.info("密码: " + serverInfo.GetConfToString("Password"));
                logger.info("ssh端口号: " + serverInfo.GetConfToString("Port"));
                logger.info("nmon安装目录: " + serverInfo.GetConfToString("NmonRoot"));
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
            logger.error("未知报错：" + e.getMessage());
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
//            logger.info("系统将在 " + sdfOut.format(runTime) + " 运行，" + args[0]);
//            Date now = new Date();
//            if (runTime.before(now)) {
//                break label192;
//            }
//            logger.info("还有 " + ((runTime.getTime() - now.getTime()) / 1000L / 60L + 1L) + "分钟运行， " + args[0]);
//            label192: Thread.sleep(60000L);
//        } catch (Exception e) {
//            logger.error("开始运行时间，输入有误！ \\\r\\\n    例如： run start 1007091430:在指定时间运行nmon（2010年7月9日14点30分）");
//            logger.error("报错信息：" + e.getMessage());
//            System.exit(0);
//        }

    }

    private static String getHelpInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("命令格式不正确！\r\n\r\n");
        stringBuffer.append("例如： \r\n");
        stringBuffer.append("run setup : 安装nmon文件\r\n");
        stringBuffer.append("run start : 运行nmon文件\n");
        stringBuffer.append("run stop : 停止nmon\n");
        stringBuffer.append("run download : 下载nmon结果\n");
        stringBuffer.append("run Uninstall : 卸载nmon\n");
//        stringBuffer.append("run start 1007091430 : 在指定时间运行nmon（2010年7月9日14点30分） ");

        return stringBuffer.toString();
    }
}
