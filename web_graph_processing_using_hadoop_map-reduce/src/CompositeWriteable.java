import java.io.IOException;
import java.util.*;
import java.io.DataInput;
import java.io.DataOutput;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
public class CompositeWriteable implements Writable {
    String in = "";
    String out = "";

    public CompositeWriteable() {}

    public CompositeWriteable(String in, String out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        in = WritableUtils.readString(input);
        out = WritableUtils.readString(input);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        WritableUtils.writeString(output, in);
        WritableUtils.writeString(output, out);
    }
    @Override
    public String toString() {
        return this.out + " " + this.in;
    }
    // @Override
    // public int compareTo(CompositeWriteable o) {
    //     return ComparisonChain.start().compare(in, o.in)
    //     .compare(out, o.out).result();
    // }

    public boolean equals(CompositeWriteable o){
        if(in.equals(o.in) && out.equals(o.out))
            return true;
        else
            return false;
    }
}