//,temp,FileSystemApplicationHistoryStore.java,539,556,temp,FileSystemApplicationHistoryStore.java,477,496
//,3
public class xxx {
  @Override
  public void containerFinished(ContainerFinishData containerFinish)
      throws IOException {
    HistoryFileWriter hfWriter =
        getHistoryFileWriter(containerFinish.getContainerId()
          .getApplicationAttemptId().getApplicationId());
    assert containerFinish instanceof ContainerFinishDataPBImpl;
    try {
      hfWriter.writeHistoryData(new HistoryDataKey(containerFinish
        .getContainerId().toString(), FINISH_DATA_SUFFIX),
        ((ContainerFinishDataPBImpl) containerFinish).getProto().toByteArray());
      LOG.info("Finish information of container "
          + containerFinish.getContainerId() + " is written");
    } catch (IOException e) {
      LOG.error("Error when writing finish information of container "
          + containerFinish.getContainerId(), e);
    }
  }

};