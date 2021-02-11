//,temp,JobSplitWriter.java,152,180,temp,JobSplitWriter.java,114,150
//,3
public class xxx {
  private static SplitMetaInfo[] writeOldSplits(
      org.apache.hadoop.mapred.InputSplit[] splits,
      FSDataOutputStream out, Configuration conf) throws IOException {
    SplitMetaInfo[] info = new SplitMetaInfo[splits.length];
    if (splits.length != 0) {
      int i = 0;
      long offset = out.getPos();
      int maxBlockLocations = conf.getInt(MRConfig.MAX_BLOCK_LOCATIONS_KEY,
          MRConfig.MAX_BLOCK_LOCATIONS_DEFAULT);
      for(org.apache.hadoop.mapred.InputSplit split: splits) {
        long prevLen = out.getPos();
        Text.writeString(out, split.getClass().getName());
        split.write(out);
        long currLen = out.getPos();
        String[] locations = split.getLocations();
        if (locations.length > maxBlockLocations) {
          LOG.warn("Max block location exceeded for split: "
              + split + " splitsize: " + locations.length +
              " maxsize: " + maxBlockLocations);
          locations = Arrays.copyOf(locations,maxBlockLocations);
        }
        info[i++] = new JobSplit.SplitMetaInfo( 
            locations, offset,
            split.getLength());
        offset += currLen - prevLen;
      }
    }
    return info;
  }

};