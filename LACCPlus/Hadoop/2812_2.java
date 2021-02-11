//,temp,RandomContainerDeletionChoosingPolicy.java,41,68,temp,TopNOrderedContainerDeletionChoosingPolicy.java,53,89
//,3
public class xxx {
  @Override
  public List<ContainerData> chooseContainerForBlockDeletion(int count,
      Map<Long, ContainerData> candidateContainers)
      throws StorageContainerException {
    Preconditions.checkNotNull(candidateContainers,
        "Internal assertion: candidate containers cannot be null");

    List<ContainerData> result = new LinkedList<>();
    List<ContainerData> orderedList = new LinkedList<>();
    orderedList.addAll(candidateContainers.values());
    Collections.sort(orderedList, CONTAINER_DATA_COMPARATOR);

    // get top N list ordered by pending deletion blocks' number
    int currentCount = 0;
    for (ContainerData entry : orderedList) {
      if (currentCount < count) {
        if (entry.getNumPendingDeletionBlocks() > 0) {
          result.add(entry);
          currentCount++;

          LOG.debug(
              "Select container {} for block deletion, "
                  + "pending deletion blocks num: {}.",
              entry.getContainerID(),
              entry.getNumPendingDeletionBlocks());
        } else {
          LOG.debug("Stop looking for next container, there is no"
              + " pending deletion block contained in remaining containers.");
          break;
        }
      } else {
        break;
      }
    }

    return result;
  }

};