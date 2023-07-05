package core;

import org.apache.log4j.Logger;
import util.initLogRecord;

public class TestLog4j {
    static Logger logger = Logger.getLogger(TestLog4j.class);

    public static void main(String[] args) {
//        initLogRecord.initLog();
        logger.debug("测试");
    }

}
