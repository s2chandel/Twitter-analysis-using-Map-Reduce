import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Date ;



public class TokenizerMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
    private final IntWritable one = new IntWritable(1);
    private IntWritable data = new IntWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

           String temp = value.toString();
            int counter = 0;
for( int i=0; i<temp.length(); i++ ) {
    if( temp.charAt(i) == ';' ) {
        counter++;
    }
}
            if (counter==3){
              String[] s = temp.split(";");
              try{
              int timelength = s[0].length();

                long timestamp = Long.parseLong(s[0]);
                Date dt = new Date(timestamp);
                data.set(dt.getHours());
                context.write(data,one);


          }
            catch(NumberFormatException e){}
        }

      }
    }
