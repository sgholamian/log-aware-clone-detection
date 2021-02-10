//,temp,DfsClientConf.java,308,321,temp,DfsClientConf.java,294,306
//,3
public class xxx {
  private static ChecksumCombineMode getChecksumCombineModeFromConf(
      Configuration conf) {
    final String mode = conf.get(
        DFS_CHECKSUM_COMBINE_MODE_KEY,
        DFS_CHECKSUM_COMBINE_MODE_DEFAULT);
    try {
      return ChecksumCombineMode.valueOf(mode);
    } catch(IllegalArgumentException iae) {
      LOG.warn("Bad checksum combine mode: {}. Using default {}", mode,
               DFS_CHECKSUM_COMBINE_MODE_DEFAULT);
      return ChecksumCombineMode.valueOf(
          DFS_CHECKSUM_COMBINE_MODE_DEFAULT);
    }
  }

};