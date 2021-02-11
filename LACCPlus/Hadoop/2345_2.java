//,temp,FileChecksumHelper.java,691,721,temp,FileChecksumHelper.java,569,601
//,3
public class xxx {
    private void tryDatanode(LocatedBlock locatedBlock,
                             DatanodeInfo datanode) throws IOException {

      ExtendedBlock block = locatedBlock.getBlock();

      try (IOStreamPair pair = getClient().connectToDN(datanode, getTimeout(),
          locatedBlock.getBlockToken())) {

        LOG.debug("write to {}: {}, block={}", datanode,
            Op.BLOCK_CHECKSUM, block);

        // get block checksum
        createSender(pair).blockChecksum(
            block,
            locatedBlock.getBlockToken(),
            new BlockChecksumOptions(getBlockChecksumType()));

        final BlockOpResponseProto reply = BlockOpResponseProto.parseFrom(
            PBHelperClient.vintPrefixed(pair.in));

        String logInfo = "for block " + block + " from datanode " +
            datanode;
        DataTransferProtoUtil.checkBlockOpStatus(reply, logInfo);

        OpBlockChecksumResponseProto checksumData =
            reply.getChecksumResponse();
        extractChecksumProperties(
            checksumData, locatedBlock, datanode, blockIdx);
        String blockChecksumForDebug = populateBlockChecksumBuf(checksumData);
        LOG.debug("got reply from {}: blockChecksum={}, blockChecksumType={}",
            datanode, blockChecksumForDebug, getBlockChecksumType());
      }
    }

};