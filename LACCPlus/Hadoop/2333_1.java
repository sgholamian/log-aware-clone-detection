//,temp,DFSTopologyNodeImpl.java,417,438,temp,DFSTopologyNodeImpl.java,380,409
//,3
public class xxx {
  public synchronized void childRemoveStorage(
      String childName, StorageType type) {
    LOG.debug("child remove storage: {}:{}", childName, type);
    Preconditions.checkArgument(childrenStorageInfo.containsKey(childName));
    EnumMap<StorageType, Integer> typeCount =
        childrenStorageInfo.get(childName);
    Preconditions.checkArgument(typeCount.containsKey(type));
    if (typeCount.get(type) > 1) {
      typeCount.put(type, typeCount.get(type) - 1);
    } else {
      typeCount.remove(type);
    }
    Preconditions.checkArgument(storageTypeCounts.containsKey(type));
    if (storageTypeCounts.get(type) > 1) {
      storageTypeCounts.put(type, storageTypeCounts.get(type) - 1);
    } else {
      storageTypeCounts.remove(type);
    }
    if (getParent() != null) {
      ((DFSTopologyNodeImpl)getParent()).childRemoveStorage(getName(), type);
    }
  }

};