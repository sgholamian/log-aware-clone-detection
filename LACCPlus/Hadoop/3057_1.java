//,temp,HadoopArchives.java,920,940,temp,HadoopArchiveLogs.java,116,136
//,3
public class xxx {
  public static void main(String[] args) {
    JobConf job = new JobConf(HadoopArchives.class);

    HadoopArchives harchives = new HadoopArchives(job);
    int ret = 0;

    try{
      ret = ToolRunner.run(harchives, args);
    } catch(Exception e) {
      LOG.debug("Exception in archives  ", e);
      System.err.println(e.getClass().getSimpleName() + " in archives");
      final String s = e.getLocalizedMessage();
      if (s != null) {
        System.err.println(s);
      } else {
        e.printStackTrace(System.err);
      }
      System.exit(1);
    }
    System.exit(ret);
  }

};