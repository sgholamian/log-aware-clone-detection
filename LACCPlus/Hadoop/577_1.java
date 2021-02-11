//,temp,TestJobHistoryParsing.java,930,942,temp,TestJobHistoryParsing.java,912,924
//,2
public class xxx {
  @Test
  public void testTaskAttemptUnsuccessfulCompletionWithoutCounters0239() throws IOException 
    {
      Path histPath = new Path(getClass().getClassLoader().getResource(
          "job_0.23.9-FAILED.jhist").getFile());
      JobHistoryParser parser = new JobHistoryParser(FileSystem.getLocal
          (new Configuration()), histPath);
      JobInfo jobInfo = parser.parse(); 
      LOG.info(" job info: " + jobInfo.getJobname() + " "
        + jobInfo.getFinishedMaps() + " " 
        + jobInfo.getTotalMaps() + " " 
        + jobInfo.getJobId() ) ;
      }

};