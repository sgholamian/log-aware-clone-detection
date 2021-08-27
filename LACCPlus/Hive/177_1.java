//,temp,TestMetastoreHousekeepingLeader.java,40,59,temp,TestMetastoreHousekeepingNonLeader.java,43,70
//,3
public class xxx {
  @Test
  public void testHouseKeepingThreadExistence() throws Exception {
    searchHousekeepingThreads();

    // Verify existence of threads
    for (Map.Entry<String, Boolean> entry : threadNames.entrySet()) {
      if (entry.getValue()) {
        LOG.info("Found thread with name " + entry.getKey());
      }
      Assert.assertTrue("No thread with name " + entry.getKey() + " found.", entry.getValue());
    }

    for (Map.Entry<Class<? extends Thread>, Boolean> entry : threadClasses.entrySet()) {
      if (entry.getValue()) {
        LOG.info("Found thread for " + entry.getKey().getSimpleName());
      }
      Assert.assertTrue("No thread found for class " + entry.getKey().getSimpleName(),
              entry.getValue());
    }
  }

};