//,temp,FileSystemApplicationHistoryStore.java,195,217,temp,StateStoreFileSystemImpl.java,164,179
//,3
public class xxx {
  @Override
  public Map<ApplicationId, ApplicationHistoryData> getAllApplications()
      throws IOException {
    Map<ApplicationId, ApplicationHistoryData> historyDataMap =
        new HashMap<ApplicationId, ApplicationHistoryData>();
    FileStatus[] files = fs.listStatus(rootDirPath);
    for (FileStatus file : files) {
      ApplicationId appId =
          ApplicationId.fromString(file.getPath().getName());
      try {
        ApplicationHistoryData historyData = getApplication(appId);
        if (historyData != null) {
          historyDataMap.put(appId, historyData);
        }
      } catch (IOException e) {
        // Eat the exception not to disturb the getting the next
        // ApplicationHistoryData
        LOG.error("History information of application " + appId
            + " is not included into the result due to the exception", e);
      }
    }
    return historyDataMap;
  }

};