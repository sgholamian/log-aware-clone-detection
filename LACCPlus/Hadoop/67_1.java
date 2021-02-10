//,temp,DistSum.java,342,352,temp,DistSum.java,264,279
//,3
public class xxx {
      @Override
      protected void map(NullWritable nw, SummationWritable sigma, final Context context
          ) throws IOException, InterruptedException {
        final Configuration conf = context.getConfiguration();
        final int nParts = conf.getInt(N_PARTS, 0);
        final Summation[] parts = sigma.getElement().partition(nParts);
        for(int i = 0; i < parts.length; ++i) {
          context.write(new IntWritable(i), new SummationWritable(parts[i]));
          LOG.info("parts[" + i + "] = " + parts[i]);
        }
      }

};