//,temp,TestMetastoreHousekeepingLeader.java,40,59,temp,TestMetastoreHousekeepingNonLeader.java,43,70
//,3
public class xxx {
  @Test
  public void testHouseKeepingThreadExistence() throws Exception {
    searchHousekeepingThreads();

    // Verify existence of threads
    for (Map.Entry<String, Boolean> entry : threadNames.entrySet()) {
      if (!entry.getValue()) {
        LOG.info("No thread found with name " + entry.getKey());
      }
      Assert.assertFalse("Thread with name " + entry.getKey() + " found.", entry.getValue());
    }

    for (Map.Entry<Class<? extends Thread>, Boolean> entry : threadClasses.entrySet()) {
      // A non-leader HMS will still run the configured number of Compaction worker threads.
      if (entry.getKey() == Worker.class) {
        if (entry.getValue()) {
          LOG.info("Thread found for " + entry.getKey().getSimpleName());
        }
        Assert.assertTrue("No thread found for " + entry.getKey().getSimpleName(), entry.getValue());
      } else {
        if (!entry.getValue()) {
          LOG.info("No thread found for " + entry.getKey().getSimpleName());
        }
        Assert.assertFalse("Thread found for class " + entry.getKey().getSimpleName(),
                entry.getValue());
      }
    }
  }

};