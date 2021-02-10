//,temp,DFSTopologyNodeImpl.java,417,438,temp,DFSTopologyNodeImpl.java,380,409
//,3
public class xxx {
  public synchronized void childAddStorage(
      String childName, StorageType type) {
    LOG.debug("child add storage: {}:{}", childName, type);
    // childrenStorageInfo should definitely contain this node already
    // because updateStorage is called after node added
    Preconditions.checkArgument(childrenStorageInfo.containsKey(childName));
    EnumMap<StorageType, Integer> typeCount =
        childrenStorageInfo.get(childName);
    if (typeCount.containsKey(type)) {
      typeCount.put(type, typeCount.get(type) + 1);
    } else {
      // Please be aware that, the counts are always "number of datanodes in
      // this subtree" rather than "number of storages in this storage".
      // so if the caller is a datanode, it should always be this branch rather
      // than the +1 branch above. This depends on the caller in
      // DatanodeDescriptor to make sure only when a *new* storage type is added
      // it calls this. (should not call this when a already existing storage
      // is added).
      // but no such restriction for inner nodes.
      typeCount.put(type, 1);
    }
    if (storageTypeCounts.containsKey(type)) {
      storageTypeCounts.put(type, storageTypeCounts.get(type) + 1);
    } else {
      storageTypeCounts.put(type, 1);
    }
    if (getParent() != null) {
      ((DFSTopologyNodeImpl)getParent()).childAddStorage(getName(), type);
    }
  }

};