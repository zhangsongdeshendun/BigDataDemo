package com.songchao.hadoop.mapreduce;

import UserAgent.UserAgentParser;
import UserAgent.UserAgent;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

public class GetLogBrowserApp {


    /**
     * Map :读取输入的文件
     */
    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        LongWritable one = new LongWritable(1);
        UserAgentParser userAgentParser = null;


        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            userAgentParser = new UserAgentParser();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //这是输入的一行数据
            String line = value.toString();
            //分割
            String source = line.substring(getCharacterPosition(line, "\"", 5)) + 1;
            UserAgent agent = userAgentParser.parse(source);
            //通过上下文把我们的map结果输出
            context.write(new Text(agent.getBrowser()), one);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            userAgentParser = null;
        }
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
     * reduce:归并操作
     */
    public static class MyReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long sum = 0;
            for (LongWritable value : values) {
                //求key出现的次数总和
                sum += value.get();
            }
            //最终统计结果的输出
            context.write(key, new LongWritable(sum));
        }

    }

    /**
     * 定义入口：封装MapReduce作业的所有信息
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //创建Configuration
        Configuration configuration = new Configuration();
        //准备清理已经存在的输出目录
        Path outputpath = new Path(args[1]);
        FileSystem fileSystem = FileSystem.get(configuration);
        if (fileSystem.exists(outputpath)) {
            fileSystem.delete(outputpath, true);
            System.out.println("file has exit,and we has deleted");
        }
        //创建JOb
        Job job = Job.getInstance(configuration, "logBrowser");
        //设置job的处理类
        job.setJarByClass(GetLogBrowserApp.class);
        //设置作业处理的输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        //设置map相关参数
        job.setMapperClass(GetLogBrowserApp.MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置reduce相关参数

        job.setReducerClass(GetLogBrowserApp.MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //设置作业处理的输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //提交作业
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
