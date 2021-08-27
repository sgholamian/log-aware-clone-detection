//,temp,sample_4814.java,2,10,temp,sample_4816.java,2,9
//,3
public class xxx {
public void commitJob(JobContext context) throws IOException {
JobConf conf = ShimLoader.getHadoopShims().getJobConf(context);
Path tmpLocation = new Path(conf.get(TMP_LOCATION));
Path finalLocation = new Path(conf.get(FINAL_LOCATION));
FileSystem fs = tmpLocation.getFileSystem(conf);


log.info("moving contents of to");
}

};