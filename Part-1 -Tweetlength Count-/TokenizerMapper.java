
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final IntWritable one = new IntWritable(1);
    private Text data = new Text();
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

           String temp = value.toString();
            int counter = 0;
for( int i=0; i<temp.length(); i++ ) {
    if( temp.charAt(i) == ';' ) {
        counter++;
    }
}
      //Filtering tweets by number of ";"
            if (counter==3){
            String[] s = temp.split(";");
            int tweetlength = s[2].length();
      //Filtering tweets with unwanted charachters and language
            if (tweetlength <=140){
      // Making bins
            for(int i =5; i<=140; i=i+5){
                  if(tweetlength<=i){
                  String print = (i-4)+"-"+(i);
                  context.write(data.set(print),one);
                  break;
                }
              }
            }
          }
        }
      }
