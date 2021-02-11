//,temp,TestMiniMRChildTask.java,375,393,temp,ResourceManager.java,596,614
//,3
public class xxx {
  @AfterClass
  public static void tearDown() {
    // close file system and shut down dfs and mapred cluster
    try {
      if (fileSys != null) {
        fileSys.close();
      }
      if (dfs != null) {
        dfs.shutdown();
      }
      if (mr != null) {
        mr.stop();
        mr = null;
      }
    } catch (IOException ioe) {
      LOG.info("IO exception in closing file system)" );
      ioe.printStackTrace();           
    }
  }

};