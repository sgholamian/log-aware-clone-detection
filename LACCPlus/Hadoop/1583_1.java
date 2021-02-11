//,temp,FileSystemApplicationHistoryStore.java,376,417,temp,FileSystemApplicationHistoryStore.java,223,264
//,3
public class xxx {
  @Override
  public Map<ContainerId, ContainerHistoryData> getContainers(
      ApplicationAttemptId appAttemptId) throws IOException {
    Map<ContainerId, ContainerHistoryData> historyDataMap =
        new HashMap<ContainerId, ContainerHistoryData>();
    HistoryFileReader hfReader =
        getHistoryFileReader(appAttemptId.getApplicationId());
    try {
      while (hfReader.hasNext()) {
        HistoryFileReader.Entry entry = hfReader.next();
        if (entry.key.id.startsWith(ConverterUtils.CONTAINER_PREFIX)) {
          ContainerId containerId =
              ConverterUtils.toContainerId(entry.key.id);
          if (containerId.getApplicationAttemptId().equals(appAttemptId)) {
            ContainerHistoryData historyData =
                historyDataMap.get(containerId);
            if (historyData == null) {
              historyData = ContainerHistoryData.newInstance(
                  containerId, null, null, null, Long.MIN_VALUE,
                  Long.MAX_VALUE, null, Integer.MAX_VALUE, null);
              historyDataMap.put(containerId, historyData);
            }
            if (entry.key.suffix.equals(START_DATA_SUFFIX)) {
              mergeContainerHistoryData(historyData,
                  parseContainerStartData(entry.value));
            } else if (entry.key.suffix.equals(FINISH_DATA_SUFFIX)) {
              mergeContainerHistoryData(historyData,
                  parseContainerFinishData(entry.value));
            }
          }
        }
      }
      LOG.info("Completed reading history information of all conatiners"
          + " of application attempt " + appAttemptId);
    } catch (IOException e) {
      LOG.info("Error when reading history information of some containers"
          + " of application attempt " + appAttemptId);
    } finally {
      hfReader.close();
    }
    return historyDataMap;
  }

};