
package com.songchao.hadoop.mapreduce;

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

/**
 * 用MapREduce开发WordCount应用程序
 */

public class ConbinerApp {

    /**
     * Map :读取输入的文件
     */
    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        LongWritable one = new LongWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //这是输入的一行数据
            String line = value.toString();
            //分割
            String[] words = line.split(" ");

            for (String word : words) {
                //通过上下文把我们的map结果输出
                context.write(new Text(word), one);
            }


        }
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
        Job job = Job.getInstance(configuration, "wordcount");
        //设置job的处理类
        job.setJarByClass(ConbinerApp.class);
        //设置作业处理的输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        //设置map相关参数
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置reduce相关参数
        job.setReducerClass(MyReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //通过Job设置combiner处理类，其实和我们的reduce是一模一样的
        job.setCombinerClass(MyReduce.class);

        //设置作业处理的输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //提交作业
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }


}
