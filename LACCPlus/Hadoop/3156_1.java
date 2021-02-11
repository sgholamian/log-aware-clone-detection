//,temp,FileSystemApplicationHistoryStore.java,262,310,temp,FileSystemApplicationHistoryStore.java,149,193
//,3
public class xxx {
  @Override
  public ApplicationAttemptHistoryData getApplicationAttempt(
      ApplicationAttemptId appAttemptId) throws IOException {
    HistoryFileReader hfReader =
        getHistoryFileReader(appAttemptId.getApplicationId());
    try {
      boolean readStartData = false;
      boolean readFinishData = false;
      ApplicationAttemptHistoryData historyData =
          ApplicationAttemptHistoryData.newInstance(appAttemptId, null, -1,
            null, null, null, FinalApplicationStatus.UNDEFINED, null);
      while ((!readStartData || !readFinishData) && hfReader.hasNext()) {
        HistoryFileReader.Entry entry = hfReader.next();
        if (entry.key.id.equals(appAttemptId.toString())) {
          if (entry.key.suffix.equals(START_DATA_SUFFIX)) {
            ApplicationAttemptStartData startData =
                parseApplicationAttemptStartData(entry.value);
            mergeApplicationAttemptHistoryData(historyData, startData);
            readStartData = true;
          } else if (entry.key.suffix.equals(FINISH_DATA_SUFFIX)) {
            ApplicationAttemptFinishData finishData =
                parseApplicationAttemptFinishData(entry.value);
            mergeApplicationAttemptHistoryData(historyData, finishData);
            readFinishData = true;
          }
        }
      }
      if (!readStartData && !readFinishData) {
        return null;
      }
      if (!readStartData) {
        LOG.warn("Start information is missing for application attempt "
            + appAttemptId);
      }
      if (!readFinishData) {
        LOG.warn("Finish information is missing for application attempt "
            + appAttemptId);
      }
      LOG.info("Completed reading history information of application attempt "
          + appAttemptId);
      return historyData;
    } catch (IOException e) {
      LOG.error("Error when reading history file of application attempt"
          + appAttemptId, e);
      throw e;
    } finally {
      hfReader.close();
    }
  }

};