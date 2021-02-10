//,temp,ActiveUsersManager.java,59,77,temp,ExcessRedundancyMap.java,77,89
//,3
public class xxx {
  synchronized boolean add(DatanodeDescriptor dn, BlockInfo blk) {
    LightWeightHashSet<BlockInfo> set = map.get(dn.getDatanodeUuid());
    if (set == null) {
      set = new LightWeightHashSet<>();
      map.put(dn.getDatanodeUuid(), set);
    }
    final boolean added = set.add(blk);
    if (added) {
      size.incrementAndGet();
      blockLog.debug("BLOCK* ExcessRedundancyMap.add({}, {})", dn, blk);
    }
    return added;
  }

};