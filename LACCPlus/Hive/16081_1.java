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

    byte[] valueBytes = currentValue.getBytes();
    int valueLength = currentValue.getLength();

    if (isNewKey) {
      slots[slot] =
          hashMapStore.addFirst(
              partialHashCode, keyBytes, keyStart, keyLength, valueBytes, 0, valueLength);
      keysAssigned++;
    } else {
      final long newRefWord =
          hashMapStore.addMore(
              refWord, valueBytes, 0, valueLength, unsafeReadPos);
      if (newRefWord != refWord) {
        slots[slot] = newRefWord;
      }
    }
  }

};