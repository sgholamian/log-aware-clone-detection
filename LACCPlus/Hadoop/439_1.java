//,temp,FileSystemApplicationHistoryStore.java,316,362,temp,FileSystemApplicationHistoryStore.java,153,197
//,3
public class xxx {
  @Override
  public ContainerHistoryData getContainer(ContainerId containerId)
      throws IOException {
    HistoryFileReader hfReader =
        getHistoryFileReader(containerId.getApplicationAttemptId()
          .getApplicationId());
    try {
      boolean readStartData = false;
      boolean readFinishData = false;
      ContainerHistoryData historyData =
          ContainerHistoryData
            .newInstance(containerId, null, null, null, Long.MIN_VALUE,
              Long.MAX_VALUE, null, Integer.MAX_VALUE, null);
      while ((!readStartData || !readFinishData) && hfReader.hasNext()) {
        HistoryFileReader.Entry entry = hfReader.next();
        if (entry.key.id.equals(containerId.toString())) {
          if (entry.key.suffix.equals(START_DATA_SUFFIX)) {
            ContainerStartData startData = parseContainerStartData(entry.value);
            mergeContainerHistoryData(historyData, startData);
            readStartData = true;
          } else if (entry.key.suffix.equals(FINISH_DATA_SUFFIX)) {
            ContainerFinishData finishData =
                parseContainerFinishData(entry.value);
            mergeContainerHistoryData(historyData, finishData);
            readFinishData = true;
          }
        }
      }
      if (!readStartData && !readFinishData) {
        return null;
      }
      if (!readStartData) {
        LOG.warn("Start information is missing for container " + containerId);
      }
      if (!readFinishData) {
        LOG.warn("Finish information is missing for container " + containerId);
      }
      LOG.info("Completed reading history information of container "
          + containerId);
      return historyData;
    } catch (IOException e) {
      LOG.error("Error when reading history file of container " + containerId, e);
      throw e;
    } finally {
      hfReader.close();
    }
  }

};