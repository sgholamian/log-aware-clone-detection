//,temp,FileSystemApplicationHistoryStore.java,515,533,temp,FileSystemApplicationHistoryStore.java,473,492
//,3
public class xxx {
  @Override
  public void applicationAttemptStarted(
      ApplicationAttemptStartData appAttemptStart) throws IOException {
    HistoryFileWriter hfWriter =
        getHistoryFileWriter(appAttemptStart.getApplicationAttemptId()
          .getApplicationId());
    assert appAttemptStart instanceof ApplicationAttemptStartDataPBImpl;
    try {
      hfWriter.writeHistoryData(new HistoryDataKey(appAttemptStart
        .getApplicationAttemptId().toString(), START_DATA_SUFFIX),
        ((ApplicationAttemptStartDataPBImpl) appAttemptStart).getProto()
          .toByteArray());
      LOG.info("Start information of application attempt "
          + appAttemptStart.getApplicationAttemptId() + " is written");
    } catch (IOException e) {
      LOG.error("Error when writing start information of application attempt "
          + appAttemptStart.getApplicationAttemptId(), e);
      throw e;
    }
  }

};