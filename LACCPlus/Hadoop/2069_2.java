//,temp,TestMapCollection.java,518,538,temp,TestMapCollection.java,497,516
//,3
public class xxx {
  @Test
  public void testRandom() throws Exception {
    Configuration conf = new Configuration();
    conf.setInt(Job.COMPLETION_POLL_INTERVAL_KEY, 100);
    Job job = Job.getInstance(conf);
    conf = job.getConfiguration();
    conf.setInt(MRJobConfig.IO_SORT_MB, 1);
    conf.setClass("test.mapcollection.class", RandomFactory.class,
        RecordFactory.class);
    final Random r = new Random();
    final long seed = r.nextLong();
    LOG.info("SEED: " + seed);
    r.setSeed(seed);
    conf.set(MRJobConfig.MAP_SORT_SPILL_PERCENT,
        Float.toString(Math.max(0.1f, r.nextFloat())));
    RandomFactory.setLengths(conf, r, 1 << 14);
    conf.setInt("test.spillmap.records", r.nextInt(500));
    conf.setLong("test.randomfactory.seed", r.nextLong());
    runTest("random", job);
  }

};