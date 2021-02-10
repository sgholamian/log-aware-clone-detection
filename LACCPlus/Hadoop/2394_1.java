//,temp,TestSortLocatedStripedBlock.java,260,299,temp,TestSortLocatedStripedBlock.java,145,176
//,3
public class xxx {
  @Test(timeout = 10000)
  public void testTargetDecommnDatanodeDoesntExists() {
    LOG.info("Starting test testTargetDecommnDatanodeDoesntExists");
    int lbsCount = 2; // two located block groups
    List<Integer> decommnNodeIndices = new ArrayList<>();
    decommnNodeIndices.add(0);
    decommnNodeIndices.add(1);
    decommnNodeIndices.add(2);
    decommnNodeIndices.add(4);
    decommnNodeIndices.add(5);

    List<Integer> targetNodeIndices = new ArrayList<>();
    targetNodeIndices.add(0);
    targetNodeIndices.add(2);
    targetNodeIndices.add(4);
    // 1 and 5 nodes doesn't exists in the target list. One such case is, the
    // target node block corrupted or lost after the successful decommissioning

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

    // After this index all are decommissioned nodes. Needs to reconstruct two
    // more block indices.
    int blkGrpWidth = dataBlocks + parityBlocks - 2;
    assertDecommnNodePosition(blkGrpWidth, decommissionedNodes, lbs);
    assertBlockIndexAndTokenPosition(lbs, locToIndexList, locToTokenList);
  }

};