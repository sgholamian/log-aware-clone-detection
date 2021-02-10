//,temp,FileChecksumHelper.java,646,685,temp,FileChecksumHelper.java,516,564
//,3
public class xxx {
    private boolean checksumBlockGroup(
        LocatedStripedBlock blockGroup) throws IOException {
      ExtendedBlock block = blockGroup.getBlock();
      long requestedNumBytes = block.getNumBytes();
      if (getRemaining() < block.getNumBytes()) {
        requestedNumBytes = getRemaining();
      }
      setRemaining(getRemaining() - requestedNumBytes);

      StripedBlockInfo stripedBlockInfo = new StripedBlockInfo(block,
          blockGroup.getLocations(), blockGroup.getBlockTokens(),
          blockGroup.getBlockIndices(), ecPolicy);
      DatanodeInfo[] datanodes = blockGroup.getLocations();

      //try each datanode in the block group.
      boolean done = false;
      for (int j = 0; !done && j < datanodes.length; j++) {
        try {
          tryDatanode(blockGroup, stripedBlockInfo, datanodes[j],
              requestedNumBytes);
          done = true;
        } catch (InvalidBlockTokenException ibte) {
          if (bgIdx > getLastRetriedIndex()) {
            LOG.debug("Got access token error in response to OP_BLOCK_CHECKSUM "
                    + "for file {} for block {} from datanode {}. Will retry "
                    + "the block once.",
                getSrc(), block, datanodes[j]);
            setLastRetriedIndex(bgIdx);
            done = true; // actually it's not done; but we'll retry
            bgIdx--; // repeat at bgIdx-th block
            setRefetchBlocks(true);
          }
        } catch (IOException ie) {
          LOG.warn("src={}" + ", datanodes[{}]={}",
              getSrc(), j, datanodes[j], ie);
        }
      }

      return done;
    }

};