//,temp,CompressionEmulationUtil.java,148,165,temp,GenerateDistCacheData.java,110,126
//,3
public class xxx {
  static void configure(final Job job) throws IOException, InterruptedException,
                                              ClassNotFoundException {
    // set the random text mapper
    job.setMapperClass(RandomTextDataMapper.class);
    job.setNumReduceTasks(0);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setInputFormatClass(GenDataFormat.class);
    job.setJarByClass(GenerateData.class);

    // set the output compression true
    FileOutputFormat.setCompressOutput(job, true);
    try {
      FileInputFormat.addInputPath(job, new Path("ignored"));
    } catch (IOException e) {
      LOG.error("Error while adding input path ", e);
    }
  }

};