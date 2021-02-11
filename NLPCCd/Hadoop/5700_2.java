//,temp,sample_6798.java,2,14,temp,sample_5824.java,2,13
//,3
public class xxx {
private ArrayList<Block> locatedToBlocks(final List<LocatedBlock> locatedBlks, List<Integer> positionsToRemove) {
ArrayList<Block> newList = new ArrayList<Block>();
for (int i = 0; i < locatedBlks.size(); i++) {
if (positionsToRemove != null && positionsToRemove.contains(i)) {
if(LOG.isDebugEnabled()) {


log.info("block to be omitted");
}
}
}
}

};