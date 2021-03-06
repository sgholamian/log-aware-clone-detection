//,temp,TestSynthJobGeneration.java,151,174,temp,TestSynthJobGeneration.java,100,124
//,2
public class xxx {
  @Test
  public void testMapReduce() throws IllegalArgumentException, IOException {

    Configuration conf = new Configuration();

    conf.set(SynthTraceJobProducer.SLS_SYNTHETIC_TRACE_FILE,
        "src/test/resources/syn.json");

    SynthTraceJobProducer stjp = new SynthTraceJobProducer(conf);

    LOG.info(stjp.toString());

    SynthJob js = (SynthJob) stjp.getNextJob();

    int jobCount = 0;

    while (js != null) {
      LOG.info(js.toString());
      validateJob(js);
      js = (SynthJob) stjp.getNextJob();
      jobCount++;
    }

    Assert.assertEquals(stjp.getNumJobs(), jobCount);
  }

};