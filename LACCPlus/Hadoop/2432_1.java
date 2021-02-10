//,temp,BlockChecksumHelper.java,723,758,temp,FileChecksumHelper.java,377,433
//,3
public class xxx {
    private void setOrVerifyChecksumProperties(int blockIdx, int bpc,
        final long cpb, DataChecksum.Type ct) throws IOException {
      //read byte-per-checksum
      if (blockIdx == 0) { //first block
        setBytesPerCRC(bpc);
      } else if (bpc != getBytesPerCRC()) {
        throw new IOException("Byte-per-checksum not matched: bpc=" + bpc
            + " but bytesPerCRC=" + getBytesPerCRC());
      }

      //read crc-per-block
      if (blockIdx == 0) {
        setCrcPerBlock(cpb);
      }

      if (blockIdx == 0) { // first block
        setCrcType(ct);
      } else if (getCrcType() != DataChecksum.Type.MIXED &&
          getCrcType() != ct) {
        BlockChecksumType groupChecksumType =
            getBlockChecksumOptions().getBlockChecksumType();
        if (groupChecksumType == BlockChecksumType.COMPOSITE_CRC) {
          throw new IOException(String.format(
              "BlockChecksumType COMPOSITE_CRC doesn't support MIXED "
              + "underlying types; previous block was %s, next block is %s",
              getCrcType(), ct));
        } else {
          setCrcType(DataChecksum.Type.MIXED);
        }
      }

      if (blockIdx == 0) {
        LOG.debug("set bytesPerCRC={}, crcPerBlock={}", getBytesPerCRC(),
            getCrcPerBlock());
      }
    }

};