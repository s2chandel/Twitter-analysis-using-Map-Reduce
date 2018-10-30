import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Hashtable;
import java.util.Set;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

	private Hashtable<String, String> athletesInfo ;
	private final IntWritable one = new IntWritable(1);
	private final Text data = new Text();

	public void map(Object key, Text value, Context context) throws  IOException, InterruptedException {
		String temp = value.toString();
	 	int counter = 0;
		for( int i=0; i<temp.length(); i++ ) {
			if(temp.charAt(i) == ';' ) {
 			 	counter++;
		 	}
	 	}
		if (counter==3){
			 String[] s = temp.split(";");
			 	Set<String> hkeys = athletesInfo.keySet();
			   for(String hkey : hkeys)	{
					 if(key != null)	{
						  if(s[2].toLowerCase().contains(hkey.toLowerCase()))	{
								String sport = athletesInfo.get(hkey);
						 		data.set(sport);
				 				context.write(data, one);
			 				}
					}
			}
		}
	}

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {

      athletesInfo = new Hashtable<String, String>();

		// We know there is only one cache file, so we only retrieve that URI
		URI fileUri = context.getCacheFiles()[0];

		FileSystem fs = FileSystem.get(context.getConfiguration());
		FSDataInputStream in = fs.open(new Path(fileUri));

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line = null;
		try {
			// we discard the header row
			br.readLine();

			while ((line = br.readLine()) != null) {
				context.getCounter(AthletesCounters.NUM_ATHLETES).increment(1);

				String[] fields = line.split(",");
				
				if (fields.length == 11) {
					athletesInfo.put(fields[1], fields[7]);
				}
			}
			br.close();
		} catch (IOException e1) {
		}

		super.setup(context);
	}
}

