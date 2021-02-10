//,temp,FileChecksumHelper.java,646,685,temp,FileChecksumHelper.java,516,564
//,3
public class xxx {
    private boolean checksumBlock(LocatedBlock locatedBlock) {
      ExtendedBlock block = locatedBlock.getBlock();
      if (getRemaining() < block.getNumBytes()) {
        block.setNumBytes(getRemaining());
      }
      setRemaining(getRemaining() - block.getNumBytes());

      DatanodeInfo[] datanodes = locatedBlock.getLocations();

      int tmpTimeout = 3000 * datanodes.length +
          getClient().getConf().getSocketTimeout();
      setTimeout(tmpTimeout);

      //try each datanode location of the block
      boolean done = false;
      for (int j = 0; !done && j < datanodes.length; j++) {
        try {
          tryDatanode(locatedBlock, datanodes[j]);
          done = true;
        } catch (InvalidBlockTokenException ibte) {
          if (blockIdx > getLastRetriedIndex()) {
            LOG.debug("Got access token error in response to OP_BLOCK_CHECKSUM "
                    + "for file {} for block {} from datanode {}. Will retry "
                    + "the block once.",
                getSrc(), block, datanodes[j]);
            setLastRetriedIndex(blockIdx);
            done = true; // actually it's not done; but we'll retry
            blockIdx--; // repeat at blockIdx-th block
            setRefetchBlocks(true);
          }
        } catch (InvalidEncryptionKeyException iee) {
          if (blockIdx > getLastRetriedIndex()) {
            LOG.debug("Got invalid encryption key error in response to "
                    + "OP_BLOCK_CHECKSUM for file {} for block {} from "
                    + "datanode {}. Will retry " + "the block once.",
                  getSrc(), block, datanodes[j]);
            setLastRetriedIndex(blockIdx);
            done = true; // actually it's not done; but we'll retry
            blockIdx--; // repeat at i-th block
            getClient().clearDataEncryptionKey();
          }
        } catch (IOException ie) {
          LOG.warn("src={}" + ", datanodes[{}]={}",
              getSrc(), j, datanodes[j], ie);
        }
      }

      return done;
    }

};