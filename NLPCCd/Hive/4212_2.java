//,temp,sample_4814.java,2,10,temp,sample_4816.java,2,9
//,3
public class xxx {
public void abortJob(JobContext context, int status) throws IOException {
JobConf conf = ShimLoader.getHadoopShims().getJobConf(context);
Path tmpLocation = new Path(conf.get(TMP_LOCATION));
FileSystem fs = tmpLocation.getFileSystem(conf);


log.info("removing");
}

};