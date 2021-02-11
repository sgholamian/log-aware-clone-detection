//,temp,FileSystemApplicationHistoryStore.java,473,492,temp,FileSystemApplicationHistoryStore.java,451,471
//,3
public class xxx {
  @Override
  public void applicationFinished(ApplicationFinishData appFinish)
      throws IOException {
    HistoryFileWriter hfWriter =
        getHistoryFileWriter(appFinish.getApplicationId());
    assert appFinish instanceof ApplicationFinishDataPBImpl;
    try {
      hfWriter.writeHistoryData(new HistoryDataKey(appFinish.getApplicationId()
        .toString(), FINISH_DATA_SUFFIX),
        ((ApplicationFinishDataPBImpl) appFinish).getProto().toByteArray());
      LOG.info("Finish information of application "
          + appFinish.getApplicationId() + " is written");
    } catch (IOException e) {
      LOG.error("Error when writing finish information of application "
          + appFinish.getApplicationId(), e);
      throw e;
    } finally {
      hfWriter.close();
      outstandingWriters.remove(appFinish.getApplicationId());
    }
  }

};