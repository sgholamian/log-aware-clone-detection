//,temp,FileSystemApplicationHistoryStore.java,515,533,temp,FileSystemApplicationHistoryStore.java,494,513
//,3
public class xxx {
  @Override
  public void containerStarted(ContainerStartData containerStart)
      throws IOException {
    HistoryFileWriter hfWriter =
        getHistoryFileWriter(containerStart.getContainerId()
          .getApplicationAttemptId().getApplicationId());
    assert containerStart instanceof ContainerStartDataPBImpl;
    try {
      hfWriter.writeHistoryData(new HistoryDataKey(containerStart
        .getContainerId().toString(), START_DATA_SUFFIX),
        ((ContainerStartDataPBImpl) containerStart).getProto().toByteArray());
      LOG.info("Start information of container "
          + containerStart.getContainerId() + " is written");
    } catch (IOException e) {
      LOG.error("Error when writing start information of container "
          + containerStart.getContainerId(), e);
      throw e;
    }
  }

};