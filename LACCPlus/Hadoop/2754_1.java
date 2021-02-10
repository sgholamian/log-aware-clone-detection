//,temp,GenerateData.java,187,200,temp,GenerateDistCacheData.java,105,129
//,3
public class xxx {
       private void configureRandomBytesDataGenerator() {
        job.setMapperClass(GenDataMapper.class);
        job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setInputFormatClass(GenDataFormat.class);
        job.setOutputFormatClass(RawBytesOutputFormat.class);
        job.setJarByClass(GenerateData.class);
        try {
          FileInputFormat.addInputPath(job, new Path("ignored"));
        } catch (IOException e) {
          LOG.error("Error while adding input path ", e);
        }
      }

};