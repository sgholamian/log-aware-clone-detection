//,temp,TestAllowFormat.java,93,104,temp,TestBlockRecovery.java,187,201
//,3
public class xxx {
  @AfterClass
  public static void tearDown() throws Exception {
    if (cluster!=null) {
      cluster.shutdown();
      LOG.info("Stopping mini cluster");
    }
    
    if ( DFS_BASE_DIR.exists() && !FileUtil.fullyDelete(DFS_BASE_DIR) ) {
      throw new IOException("Could not delete hdfs directory in tearDown '"
                            + DFS_BASE_DIR + "'");
    }	
  }

};