package hdfs;

import UserAgent.UserAgent;
import UserAgent.UserAgentParser;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * userAgent测试类
 */

public class UserAgentTest {


    public void FileReadTest() throws Exception {
        File file = new File("/Users/zhangsongdeshendun/Downloads/100_access.log");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line = "";
        while (line != null) {
            line = bufferedReader.readLine();//依次读入一行数据
            if (StringUtils.isNotBlank(line)) {

            }

        }
    }


    /**
     * 单元测试：UserAgentTest
     */
    @Test
    public void userAgentTest() {
        String source = "Yammer 4.1.1.668 (iPhone; iPhone OS 5.0.1; en_US)";

        UserAgentParser userAgentParser = new UserAgentParser();
        UserAgent agent = userAgentParser.parse(source);

        String browser = agent.getBrowser();
        String engine = agent.getEngine();
        String engineVersion = agent.getEngineVersion();
        String os = agent.getOs();
        String platform = agent.getPlatform();
        boolean isMoblie = agent.isMobile();
        System.out.println(browser + "  " + engine + "  " + engineVersion + "  " + os + "  " + platform + "  " + isMoblie);
    }


}
