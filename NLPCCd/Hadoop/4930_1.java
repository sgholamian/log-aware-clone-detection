//,temp,sample_8594.java,2,12,temp,sample_8593.java,2,9
//,3
public class xxx {
public void map(LongWritable key, Text val, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
String str = val.toString();
if(MAPPER_BAD_RECORDS.get(0).equals(str)) {
System.exit(-1);
}
else if(MAPPER_BAD_RECORDS.get(1).equals(str)) {


log.info("map encountered bad record");
}
}

};