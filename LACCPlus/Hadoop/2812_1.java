//,temp,RandomContainerDeletionChoosingPolicy.java,41,68,temp,TopNOrderedContainerDeletionChoosingPolicy.java,53,89
//,3
public class xxx {
  @Override
  public List<ContainerData> chooseContainerForBlockDeletion(int count,
      Map<Long, ContainerData> candidateContainers)
      throws StorageContainerException {
    Preconditions.checkNotNull(candidateContainers,
        "Internal assertion: candidate containers cannot be null");

    int currentCount = 0;
    List<ContainerData> result = new LinkedList<>();
    ContainerData[] values = new ContainerData[candidateContainers.size()];
    // to get a shuffle list
    for (ContainerData entry : DFSUtil.shuffle(
        candidateContainers.values().toArray(values))) {
      if (currentCount < count) {
        result.add(entry);
        currentCount++;

        LOG.debug("Select container {} for block deletion, "
            + "pending deletion blocks num: {}.",
            entry.getContainerID(),
            entry.getNumPendingDeletionBlocks());
      } else {
        break;
      }
    }

    return result;
  }

};