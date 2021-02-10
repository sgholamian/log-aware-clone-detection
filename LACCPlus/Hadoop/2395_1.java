//,temp,TestSortLocatedStripedBlock.java,145,176,temp,TestSortLocatedStripedBlock.java,92,121
//,3
public class xxx {
  @Test(timeout = 10000)
  public void testTwoDatanodesWithSameBlockIndexAreDecommn() {
    LOG.info("Starting test testTwoDatanodesWithSameBlockIndexAreDecommn");
    int lbsCount = 2; // two located block groups
    List<Integer> decommnNodeIndices = new ArrayList<>();
    decommnNodeIndices.add(0);
    decommnNodeIndices.add(1);
    decommnNodeIndices.add(4);
    decommnNodeIndices.add(5);
    // representing blockIndex 1, later this also decommissioned
    decommnNodeIndices.add(1);

    List<Integer> targetNodeIndices = new ArrayList<>();
    targetNodeIndices.addAll(decommnNodeIndices);
    // map contains decommissioned node details in each located strip block
    // which will be used for assertions
    HashMap<Integer, List<String>> decommissionedNodes = new HashMap<>(
        lbsCount * decommnNodeIndices.size());
    List<LocatedBlock> lbs = createLocatedStripedBlocks(lbsCount,
        dataBlocks, parityBlocks, decommnNodeIndices,
        targetNodeIndices, decommissionedNodes);

    // prepare expected block index and token list.
    List<HashMap<DatanodeInfo, Byte>> locToIndexList = new ArrayList<>();
    List<HashMap<DatanodeInfo, Token<BlockTokenIdentifier>>> locToTokenList =
        new ArrayList<>();
    prepareBlockIndexAndTokenList(lbs, locToIndexList, locToTokenList);

    dm.sortLocatedBlocks(null, lbs);
    assertDecommnNodePosition(groupSize, decommissionedNodes, lbs);
    assertBlockIndexAndTokenPosition(lbs, locToIndexList, locToTokenList);
  }

};