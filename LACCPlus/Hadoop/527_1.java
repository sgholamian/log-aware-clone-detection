//,temp,TestJobHistoryParsing.java,912,924,temp,TestJobHistoryParsing.java,894,906
//,2
public class xxx {
  @Test
  public void testTaskAttemptUnsuccessfulCompletionWithoutCounters240() throws IOException 
    {
      Path histPath = new Path(getClass().getClassLoader().getResource(
        "job_2.4.0-FAILED.jhist").getFile());
      JobHistoryParser parser = new JobHistoryParser(FileSystem.getLocal
          (new Configuration()), histPath);
      JobInfo jobInfo = parser.parse(); 
      LOG.info(" job info: " + jobInfo.getJobname() + " "
        + jobInfo.getFinishedMaps() + " "
        + jobInfo.getTotalMaps() + " "
        + jobInfo.getJobId() );
    }

};