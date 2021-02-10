//,temp,BlockChecksumHelper.java,723,758,temp,FileChecksumHelper.java,377,433
//,3
public class xxx {
    void extractChecksumProperties(
        OpBlockChecksumResponseProto checksumData,
        LocatedBlock locatedBlock,
        DatanodeInfo datanode,
        int blockIdx)
        throws IOException {
      //read byte-per-checksum
      final int bpc = checksumData.getBytesPerCrc();
      if (blockIdx == 0) { //first block
        setBytesPerCRC(bpc);
      } else if (bpc != getBytesPerCRC()) {
        if (getBlockChecksumType() == BlockChecksumType.COMPOSITE_CRC) {
          LOG.warn(
              "Current bytesPerCRC={} doesn't match next bpc={}, but "
              + "continuing anyway because we're using COMPOSITE_CRC. "
              + "If trying to preserve CHECKSUMTYPE, only the current "
              + "bytesPerCRC will be preserved.", getBytesPerCRC(), bpc);
        } else {
          throw new IOException("Byte-per-checksum not matched: bpc=" + bpc
              + " but bytesPerCRC=" + getBytesPerCRC());
        }
      }

      //read crc-per-block
      final long cpb = checksumData.getCrcPerBlock();
      if (getLocatedBlocks().size() > 1 && blockIdx == 0) {
        setCrcPerBlock(cpb);
      }

      // read crc-type
      final DataChecksum.Type ct;
      if (checksumData.hasCrcType()) {
        ct = PBHelperClient.convert(checksumData.getCrcType());
      } else {
        LOG.debug("Retrieving checksum from an earlier-version DataNode: " +
            "inferring checksum by reading first byte");
        ct = getClient().inferChecksumTypeByReading(locatedBlock, datanode);
      }

      if (blockIdx == 0) {
        setCrcType(ct);
      } else if (getCrcType() != DataChecksum.Type.MIXED &&
          getCrcType() != ct) {
        if (getBlockChecksumType() == BlockChecksumType.COMPOSITE_CRC) {
          throw new IOException(
              "DataChecksum.Type.MIXED is not supported for COMPOSITE_CRC");
        } else {
          // if crc types are mixed in a file
          setCrcType(DataChecksum.Type.MIXED);
        }
      }

      if (blockIdx == 0) {
        LOG.debug("set bytesPerCRC={}, crcPerBlock={}",
            getBytesPerCRC(), getCrcPerBlock());
      }
    }

};