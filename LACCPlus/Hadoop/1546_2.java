//,temp,CompressionEmulationUtil.java,148,165,temp,GenerateDistCacheData.java,110,126
//,3
public class xxx {
       public Job run() throws IOException, ClassNotFoundException,
                               InterruptedException {
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
          LOG.error("Error while adding input path ", e);
        }
        job.submit();
        return job;
      }

};