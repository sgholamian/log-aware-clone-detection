//,temp,DistSum.java,342,352,temp,DistSum.java,264,279
//,3
public class xxx {
      @Override
      public List<InputSplit> getSplits(JobContext context) {
        //read sigma from conf
        final Configuration conf = context.getConfiguration();
        final Summation sigma = SummationWritable.read(DistSum.class, conf); 
        final int nParts = conf.getInt(N_PARTS, 0);
  
        //create splits
        final List<InputSplit> splits = new ArrayList<InputSplit>(nParts);
        final Summation[] parts = sigma.partition(nParts);
        for(int i = 0; i < parts.length; ++i) {
          splits.add(new SummationSplit(parts[i]));
          //LOG.info("parts[" + i + "] = " + parts[i]);
        }
        return splits;
      }

};