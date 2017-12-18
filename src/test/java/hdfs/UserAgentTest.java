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
        String source = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; MDDR; InfoPath.2; .NET4.0C)";

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
