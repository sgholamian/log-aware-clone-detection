//,temp,sample_665.java,2,10,temp,sample_669.java,2,9
//,3
public class xxx {
private void runSmallBlockersDiscard(BuddyAllocator a, int baseSize, boolean deallocOneFirst, boolean deallocOneSecond) {
LlapAllocatorBuffer[] initial = prepareAllocatorWithSmallFragments( a, baseSize, deallocOneFirst, deallocOneSecond, true);
int bigAllocSize = baseSize * 4;
LlapAllocatorBuffer[] after = allocate(a, 1, bigAllocSize, 1 + initial.length);


log.info("after");
}

};