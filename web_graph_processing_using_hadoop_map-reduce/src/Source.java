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


public class Source {

 public static class Map extends Mapper<LongWritable, Text, Text, Text> {
    private final static IntWritable one = new IntWritable(1);
    private Text in = new Text();
    private Text out = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        out = new Text(tokenizer.nextToken());
        in = new Text(tokenizer.nextToken());
        //must keep track of where to/from so as not to duplicate count
        context.write(in, out); 
    }
 }

 public static class Reduce extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
        HashMap<String,String> seen = new HashMap<String,String>();
        String degree;
        ArrayList<String> out = new ArrayList<String>();
        for (Text val : values) {
            degree = seen.get(val.toString());
            if(degree == null){
                seen.put(val.toString(), "");
                out.add(val.toString());
            }
        }
        Collections.sort(out);
        String output = "[";
        for(String s: out){
            output += (s + " ");
        }
        output += "]";
        context.write(key, new Text(output));
    }
 }

 public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();

    Job job = new Job(conf, "part2");
	job.setJarByClass(Source.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
 }
}
