//,temp,DfsClientConf.java,308,321,temp,DfsClientConf.java,294,306
//,3
public class xxx {
  private static DataChecksum.Type getChecksumType(Configuration conf) {
    final String checksum = conf.get(
        DFS_CHECKSUM_TYPE_KEY,
        DFS_CHECKSUM_TYPE_DEFAULT);
    try {
      return DataChecksum.Type.valueOf(checksum);
    } catch(IllegalArgumentException iae) {
      LOG.warn("Bad checksum type: {}. Using default {}", checksum,
               DFS_CHECKSUM_TYPE_DEFAULT);
      return DataChecksum.Type.valueOf(
          DFS_CHECKSUM_TYPE_DEFAULT);
    }
  }

};