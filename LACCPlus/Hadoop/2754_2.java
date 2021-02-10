//,temp,GenerateData.java,187,200,temp,GenerateDistCacheData.java,105,129
//,3
public class xxx {
  @Override
  public Job call() throws IOException, InterruptedException,
                           ClassNotFoundException {
    UserGroupInformation ugi = UserGroupInformation.getLoginUser();
    ugi.doAs( new PrivilegedExceptionAction <Job>() {
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
    });
    return job;
  }

};