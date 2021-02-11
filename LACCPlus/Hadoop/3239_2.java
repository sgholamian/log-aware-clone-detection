//,temp,FileSystemApplicationHistoryStore.java,312,358,temp,FileSystemApplicationHistoryStore.java,149,193
//,3
public class xxx {
  @Override
  public ApplicationHistoryData getApplication(ApplicationId appId)
      throws IOException {
    HistoryFileReader hfReader = getHistoryFileReader(appId);
    try {
      boolean readStartData = false;
      boolean readFinishData = false;
      ApplicationHistoryData historyData =
          ApplicationHistoryData.newInstance(appId, null, null, null, null,
            Long.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE, null,
            FinalApplicationStatus.UNDEFINED, null);
      while ((!readStartData || !readFinishData) && hfReader.hasNext()) {
        HistoryFileReader.Entry entry = hfReader.next();
        if (entry.key.id.equals(appId.toString())) {
          if (entry.key.suffix.equals(START_DATA_SUFFIX)) {
            ApplicationStartData startData =
                parseApplicationStartData(entry.value);
            mergeApplicationHistoryData(historyData, startData);
            readStartData = true;
          } else if (entry.key.suffix.equals(FINISH_DATA_SUFFIX)) {
            ApplicationFinishData finishData =
                parseApplicationFinishData(entry.value);
            mergeApplicationHistoryData(historyData, finishData);
            readFinishData = true;
          }
        }
      }
      if (!readStartData && !readFinishData) {
        return null;
      }
      if (!readStartData) {
        LOG.warn("Start information is missing for application " + appId);
      }
      if (!readFinishData) {
        LOG.warn("Finish information is missing for application " + appId);
      }
      LOG.info("Completed reading history information of application " + appId);
      return historyData;
    } catch (IOException e) {
      LOG.error("Error when reading history file of application " + appId, e);
      throw e;
    } finally {
      hfReader.close();
    }
  }

};