package hdfs;

//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;

//import java.net.URI;

public class HDFSApp {

//    public static final String HDFS_PATH = "hdfs://10.129.22.194:8020";
//
//    FileSystem fileSystem = null;
//    Configuration configuration = null;
//
//    @Before
//    public void setUp() throws Exception {
//        System.out.print("hdfs:setUp");
//        configuration = new Configuration();
//        configuration.set("fs.defaultFS", "hdfs://10.129.22.194:8020");
//        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "root");
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        configuration = null;
//        fileSystem = null;
//        System.out.print("hdfs:tearDown");
//    }
//
//    /**
//     * 创建文件夹
//     *
//     * @throws Exception
//     */
//    @Test
//    public void mkdir() throws Exception {
//        fileSystem.mkdirs(new Path("/hdfsapi"));
//    }
//
//    /**
//     * 创建文件
//     *
//     * @throws Exception
//     */
//    @Test
//    public void create() throws Exception {
//        FSDataOutputStream outputStream = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
//        outputStream.write("hello hadoop".getBytes());
//        outputStream.flush();
//        outputStream.close();
//    }
//
//    /**
//     * 查看文件内容
//     *
//     * @throws Exception
//     */
//    @Test
//    public void cat() throws Exception {
//        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/test/a.txt"));
//        IOUtils.copyBytes(in, System.out, 1024);
//        in.close();
//    }
//
//    /**
//     * 改名字
//     */
//    @Test
//    public void rename() throws Exception {
//        Path oldPath = new Path("/hdfsapi/test/a.txt");
//        Path newPath = new Path("/hdfsapi/test/b.txt");
//        fileSystem.rename(oldPath, newPath);
//    }
//
//    /**
//     * 把一个本地文件上传到hdfs
//     */
//    @Test
//    public void copyFromLocalFile() throws Exception {
//        Path localPath = new Path("/root/file1");
//        Path destinationPath = new Path("/hdfsapi/test/");
//        fileSystem.copyFromLocalFile(localPath, destinationPath);
//    }


}
