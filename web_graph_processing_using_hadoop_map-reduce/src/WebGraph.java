import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class WebGraph {

 public static class Map extends Mapper<LongWritable, Text, Text, CompositeWriteable> {
    private final static IntWritable one = new IntWritable(1);
    private Text in = new Text();
    private Text out = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        out = new Text(tokenizer.nextToken());
        in = new Text(tokenizer.nextToken());
        //must keep track of where to/from so as not to duplicate count
        context.write(out, new CompositeWriteable(in.toString(), "out"));
        context.write(in,new CompositeWriteable(out.toString(), "in")); 
    }
 }

 public static class Reduce extends Reducer<Text, CompositeWriteable, Text, CompositeWriteable> {

    public void reduce(Text key, Iterable<CompositeWriteable> values, Context context)
      throws IOException, InterruptedException {
        HashMap<String,String> seen = new HashMap<String,String>();
        int inSum = 0;
        int outSum = 0;
        String degree =null;
        System.out.println("key: " + key);
        for (CompositeWriteable val : values) {
            degree = seen.get(val.in+val.out);
            if(degree == null){
                seen.put(val.in + val.out, val.out);
                if(val.out.equals("out"))
                    outSum += 1;
                else
                    inSum += 1;
            }else if(!degree.equals(val.out)){
                if(val.out.equals("out"))
                    outSum += 1;
                else
                    inSum += 1;
                seen.put(val.in+val.out, val.out);
            }
        }
        context.write(key, new CompositeWriteable(Integer.toString(inSum),Integer.toString(outSum)));
    }
 }

 public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();

    Job job = new Job(conf, "degree count");
	job.setJarByClass(WebGraph.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(CompositeWriteable.class);

    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
 }
}
