//,temp,sample_5417.java,2,17,temp,sample_7435.java,2,17
//,2
public class xxx {
public void dummy_method(){
job.setMapperClass(GenDCDataMapper.class);
job.setNumReduceTasks(0);
job.setMapOutputKeyClass(NullWritable.class);
job.setMapOutputValueClass(BytesWritable.class);
job.setInputFormatClass(GenDCDataFormat.class);
job.setOutputFormatClass(NullOutputFormat.class);
job.setJarByClass(GenerateDistCacheData.class);
try {
FileInputFormat.addInputPath(job, new Path("ignored"));
} catch (IOException e) {


log.info("error while adding input path");
}
}

};