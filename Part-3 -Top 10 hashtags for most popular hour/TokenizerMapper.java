import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Date ;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


    public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final IntWritable one = new IntWritable(1);
    
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
              int hour = dt.getHours();

              if(hour ==2) {
                        String tweet = s[2];

                        Matcher m = Pattern.compile("(#\\w+)\\b",Pattern.CASE_INSENSITIVE).matcher(s[2]);
                        while(m.find()){

                          context.write(new Text(m.group(1).toLowerCase()),one);
                        }
                      }
                    }
                      catch(NumberFormatException e){}
                  }

                }
              }

