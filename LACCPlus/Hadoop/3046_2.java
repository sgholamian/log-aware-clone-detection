//,temp,TestFileSystemStorageStatistics.java,81,92,temp,ITestAzureHugeFiles.java,167,175
//,3
public class xxx {
  private void logFSState() {
    StorageStatistics statistics = getFileSystem().getStorageStatistics();
    Iterator<StorageStatistics.LongStatistic> longStatistics
        = statistics.getLongStatistics();
    while (longStatistics.hasNext()) {
      StorageStatistics.LongStatistic next = longStatistics.next();
      LOG.info("{} = {}", next.getName(), next.getValue());
    }
  }

};