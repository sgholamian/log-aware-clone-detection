//,temp,QueryCompactor.java,262,273,temp,CompactorMR.java,1036,1043
//,3
public class xxx {
    @Override
    public void abortJob(JobContext context, int status) throws IOException {
      JobConf conf = ShimLoader.getHadoopShims().getJobConf(context);
      Path tmpLocation = new Path(conf.get(TMP_LOCATION));
      FileSystem fs = tmpLocation.getFileSystem(conf);
      LOG.debug("Removing " + tmpLocation.toString());
      fs.delete(tmpLocation, true);
    }

};