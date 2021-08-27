//,temp,VectorMapJoinFastBytesHashMap.java,155,213,temp,VectorMapJoinFastBytesHashSet.java,47,98
//,3
public class xxx {
  public void add(byte[] keyBytes, int keyStart, int keyLength, BytesWritable currentValue) {

    if (checkResize()) {
      expandAndRehash();
    }

    long hashCode = HashCodeUtil.murmurHash(keyBytes, keyStart, keyLength);
    int intHashCode = (int) hashCode;
    int slot = (intHashCode & logicalHashBucketMask);
    long probeSlot = slot;
    int i = 0;
    boolean isNewKey;
    long refWord;
    final long partialHashCode =
        VectorMapJoinFastBytesHashKeyRef.extractPartialHashCode(hashCode);
    while (true) {
      refWord = slots[slot];
      if (refWord == 0) {
        isNewKey = true;
        break;
      }
      if (VectorMapJoinFastBytesHashKeyRef.getPartialHashCodeFromRefWord(refWord) ==
              partialHashCode &&
          VectorMapJoinFastBytesHashKeyRef.equalKey(
              refWord, keyBytes, keyStart, keyLength, writeBuffers, unsafeReadPos)) {
        isNewKey = false;
        break;
      }
      ++metricPutConflict;
      // Some other key (collision) - keep probing.
      probeSlot += (++i);
      slot = (int) (probeSlot & logicalHashBucketMask);
    }

    if (largestNumberOfSteps < i) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Probed " + i + " slots (the longest so far) to find space");
      }
      largestNumberOfSteps = i;
      // debugDumpKeyProbe(keyOffset, keyLength, hashCode, slot);
    }

    if (isNewKey) {
      slots[slot] =
          hashSetStore.add(
              partialHashCode, keyBytes, keyStart, keyLength);
      keysAssigned++;
    } else {

      // Key already exists -- do nothing.
    }
  }

};