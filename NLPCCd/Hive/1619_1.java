//,temp,sample_665.java,2,10,temp,sample_669.java,2,9
//,3
public class xxx {
private static void runZebraDiscard( BuddyAllocator a, int baseSize, int pairCount, int allocs) {
LlapAllocatorBuffer[] initial = prepareZebraFragmentedAllocator(a, baseSize, pairCount, true);
int allocFraction = allocs * 2;
int bigAllocSize = pairCount * 2 * baseSize / allocFraction;
LlapAllocatorBuffer[] after = allocate(a, allocs, bigAllocSize, 1 + initial.length);


log.info("after");
}

};