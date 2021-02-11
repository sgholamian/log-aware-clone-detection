//,temp,TestFileSystemStorageStatistics.java,81,92,temp,ITestAzureHugeFiles.java,167,175
//,3
public class xxx {
  @Test
  public void testGetLongStatistics() {
    Iterator<LongStatistic> iter = storageStatistics.getLongStatistics();
    while (iter.hasNext()) {
      final LongStatistic longStat = iter.next();
      assertNotNull(longStat);
      final long expectedStat = getStatisticsValue(longStat.getName());
      LOG.info("{}: FileSystem.Statistics={}, FileSystemStorageStatistics={}",
          longStat.getName(), expectedStat, longStat.getValue());
      assertEquals(expectedStat, longStat.getValue());
    }
  }

};