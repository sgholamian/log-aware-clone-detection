//,temp,TestJobHistoryParsing.java,930,942,temp,TestJobHistoryParsing.java,894,906
//,2
public class xxx {
  @Test
  public void testTaskAttemptUnsuccessfulCompletionWithoutCounters203() throws IOException 
    { 
      Path histPath = new Path(getClass().getClassLoader().getResource(
        "job_2.0.3-alpha-FAILED.jhist").getFile());
      JobHistoryParser parser = new JobHistoryParser(FileSystem.getLocal
          (new Configuration()), histPath);
      JobInfo jobInfo = parser.parse(); 
      LOG.info(" job info: " + jobInfo.getJobname() + " "
        + jobInfo.getFinishedMaps() + " " 
        + jobInfo.getTotalMaps() + " " 
        + jobInfo.getJobId() ) ;
    }

};