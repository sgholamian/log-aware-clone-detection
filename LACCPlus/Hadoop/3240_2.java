//,temp,FileSystemApplicationHistoryStore.java,312,358,temp,FileSystemApplicationHistoryStore.java,219,260
//,3
public class xxx {
  @Override
  public Map<ApplicationAttemptId, ApplicationAttemptHistoryData>
      getApplicationAttempts(ApplicationId appId) throws IOException {
    Map<ApplicationAttemptId, ApplicationAttemptHistoryData> historyDataMap =
        new HashMap<ApplicationAttemptId, ApplicationAttemptHistoryData>();
    HistoryFileReader hfReader = getHistoryFileReader(appId);
    try {
      while (hfReader.hasNext()) {
        HistoryFileReader.Entry entry = hfReader.next();
        if (entry.key.id.startsWith(
            ConverterUtils.APPLICATION_ATTEMPT_PREFIX)) {
          ApplicationAttemptId appAttemptId = ApplicationAttemptId.fromString(
              entry.key.id);
          if (appAttemptId.getApplicationId().equals(appId)) {
            ApplicationAttemptHistoryData historyData = 
                historyDataMap.get(appAttemptId);
            if (historyData == null) {
              historyData = ApplicationAttemptHistoryData.newInstance(
                  appAttemptId, null, -1, null, null, null,
                  FinalApplicationStatus.UNDEFINED, null);
              historyDataMap.put(appAttemptId, historyData);
            }
            if (entry.key.suffix.equals(START_DATA_SUFFIX)) {
              mergeApplicationAttemptHistoryData(historyData,
                  parseApplicationAttemptStartData(entry.value));
            } else if (entry.key.suffix.equals(FINISH_DATA_SUFFIX)) {
              mergeApplicationAttemptHistoryData(historyData,
                  parseApplicationAttemptFinishData(entry.value));
            }
          }
        }
      }
      LOG.info("Completed reading history information of all application"
          + " attempts of application " + appId);
    } catch (IOException e) {
      LOG.info("Error when reading history information of some application"
          + " attempts of application " + appId);
    } finally {
      hfReader.close();
    }
    return historyDataMap;
  }

};