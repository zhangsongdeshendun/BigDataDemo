package hdfs;

import UserAgent.UserAgent;
import UserAgent.UserAgentParser;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * userAgent测试类
 */

public class UserAgentTest {

    /**
     * 测试用java读取日志文件，同时获得浏览器的出现次数
     *
     * @throws Exception
     */
    @Test
    public void FileReadTest() throws Exception {
        File file = new File("/Users/zhangsongdeshendun/Downloads/100_access.log");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        UserAgentParser userAgentParser = new UserAgentParser();
        String line = "";
        int counts = 0;
        Map<String, Integer> browserMap = new HashMap<>();
        while (line != null) {
            line = bufferedReader.readLine();//依次读入一行数据
            if (StringUtils.isNotBlank(line)) {
                counts++;
                String source = line.substring(getCharacterPosition(line, "\"", 5)) + 1;
                UserAgent agent = userAgentParser.parse(source);
                String browser = agent.getBrowser();
                String engine = agent.getEngine();
                String engineVersion = agent.getEngineVersion();
                String os = agent.getOs();
                String platform = agent.getPlatform();
                boolean isMoblie = agent.isMobile();
                System.out.println(browser + "  " + engine + "  " + engineVersion + "  " + os + "  " + platform + "  " + isMoblie);

                if (!browserMap.containsKey(browser)) {
                    browserMap.put(browser, 1);
                } else {
                    browserMap.put(browser, browserMap.get(browser) + 1);
                }
            }

        }
        System.out.println(counts);

        for (Map.Entry<String, Integer> entry : browserMap.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
    }

    /**
     * 测试getCharacterPosition方法
     */
    @Test
    public void testGetCharacterPosition() {
        String value = "183.49.46.228 - - [18/Sep/2013:06:49:23 +0000] \"-\" 400 0 \"-\" \"-\"";
        int index = getCharacterPosition(value, "\"", 5);
        System.out.println(index);
    }

    /**
     * 获取指定字符串中指定标识的字符串出现的索引位置
     */
    private static int getCharacterPosition(String value, String operator, int index) {
        Matcher slashMatcher = Pattern.compile(operator).matcher(value);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            if (mIdx == index) {
                break;
            }
        }
        return slashMatcher.start();
    }


    /**
     * 单元测试：UserAgentTest
     */
    @Test
    public void userAgentTest() {
        String source = "Mozilla/4.0 (compatible;)";

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
