//,temp,FileChecksumHelper.java,691,721,temp,FileChecksumHelper.java,569,601
//,3
public class xxx {
    private void tryDatanode(LocatedStripedBlock blockGroup,
                             StripedBlockInfo stripedBlockInfo,
                             DatanodeInfo datanode,
                             long requestedNumBytes) throws IOException {
      try (IOStreamPair pair = getClient().connectToDN(datanode,
          getTimeout(), blockGroup.getBlockToken())) {

        LOG.debug("write to {}: {}, blockGroup={}",
            datanode, Op.BLOCK_GROUP_CHECKSUM, blockGroup);

        // get block group checksum
        createSender(pair).blockGroupChecksum(
            stripedBlockInfo,
            blockGroup.getBlockToken(),
            requestedNumBytes,
            new BlockChecksumOptions(getBlockChecksumType()));

        BlockOpResponseProto reply = BlockOpResponseProto.parseFrom(
            PBHelperClient.vintPrefixed(pair.in));

        String logInfo = "for blockGroup " + blockGroup +
            " from datanode " + datanode;
        DataTransferProtoUtil.checkBlockOpStatus(reply, logInfo);

        OpBlockChecksumResponseProto checksumData = reply.getChecksumResponse();
        extractChecksumProperties(checksumData, blockGroup, datanode, bgIdx);
        String blockChecksumForDebug = populateBlockChecksumBuf(checksumData);
        LOG.debug("got reply from {}: blockChecksum={}, blockChecksumType={}",
            datanode, blockChecksumForDebug, getBlockChecksumType());
      }
    }

};