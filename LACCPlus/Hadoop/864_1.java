//,temp,FileSystemApplicationHistoryStore.java,498,517,temp,FileSystemApplicationHistoryStore.java,455,475
//,3
public class xxx {
  @Override
  public void applicationAttemptFinished(
      ApplicationAttemptFinishData appAttemptFinish) throws IOException {
    HistoryFileWriter hfWriter =
        getHistoryFileWriter(appAttemptFinish.getApplicationAttemptId()
          .getApplicationId());
    assert appAttemptFinish instanceof ApplicationAttemptFinishDataPBImpl;
    try {
      hfWriter.writeHistoryData(new HistoryDataKey(appAttemptFinish
        .getApplicationAttemptId().toString(), FINISH_DATA_SUFFIX),
        ((ApplicationAttemptFinishDataPBImpl) appAttemptFinish).getProto()
          .toByteArray());
      LOG.info("Finish information of application attempt "
          + appAttemptFinish.getApplicationAttemptId() + " is written");
    } catch (IOException e) {
      LOG.error("Error when writing finish information of application attempt "
          + appAttemptFinish.getApplicationAttemptId(), e);
      throw e;
    }
  }

};