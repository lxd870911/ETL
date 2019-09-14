import org.apache.log4j.Logger;
import top.wetech.tools.datetime.DateTimeUtils;

/**
 * company:
 * user: chenzuoli
 * date: 2018/6/14
 * time: 13:41
 * description: 测试DateTimeUtils工具类
 */

public class TestDateTimeUtils {
    private static Logger logger = Logger.getLogger(TestDateTimeUtils.class);

    public static void main(String[] args) {
        t1();
    }

    private static void t1() {
        String dateStr = "2/1/18";
        System.out.println(DateTimeUtils.dateStr2DateStr(dateStr));
        logger.info(DateTimeUtils.dateStr2DateStr(dateStr));
    }

}
