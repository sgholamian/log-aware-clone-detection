//,temp,TestJobHistoryParsing.java,524,581,temp,TestJobHistoryParsing.java,461,522
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testCountersForFailedTask() throws Exception {
    LOG.info("STARTING testCountersForFailedTask");
    try {
      Configuration conf = new Configuration();
      conf.setClass(
          NET_TOPOLOGY_NODE_SWITCH_MAPPING_IMPL_KEY,
          MyResolver.class, DNSToSwitchMapping.class);
      RackResolver.init(conf);
      MRApp app = new MRAppWithHistoryWithFailedTask(2, 1, true, this
          .getClass().getName(), true);
      app.submit(conf);
      Job job = app.getContext().getAllJobs().values().iterator().next();
      JobId jobId = job.getID();
      app.waitForState(job, JobState.FAILED);

      // make sure all events are flushed
      app.waitForState(Service.STATE.STOPPED);

      JobHistory jobHistory = new JobHistory();
      jobHistory.init(conf);

      HistoryFileInfo fileInfo = jobHistory.getJobFileInfo(jobId);
      
      JobHistoryParser parser;
      JobInfo jobInfo;
      synchronized (fileInfo) {
        Path historyFilePath = fileInfo.getHistoryFile();
        FSDataInputStream in = null;
        FileContext fc = null;
        try {
          fc = FileContext.getFileContext(conf);
          in = fc.open(fc.makeQualified(historyFilePath));
        } catch (IOException ioe) {
          LOG.info("Can not open history file: " + historyFilePath, ioe);
          throw (new Exception("Can not open History File"));
        }

        parser = new JobHistoryParser(in);
        jobInfo = parser.parse();
      }
      Exception parseException = parser.getParseException();
      Assert.assertNull("Caught an expected exception " + parseException,
          parseException);
      for (Map.Entry<TaskID, TaskInfo> entry : jobInfo.getAllTasks().entrySet()) {
        TaskId yarnTaskID = TypeConverter.toYarn(entry.getKey());
        CompletedTask ct = new CompletedTask(yarnTaskID, entry.getValue());
        Assert.assertNotNull("completed task report has null counters", ct
            .getReport().getCounters());
      }
      final List<String> originalDiagnostics = job.getDiagnostics();
      final String historyError = jobInfo.getErrorInfo();
      assertTrue("No original diagnostics for a failed job",
          originalDiagnostics != null && !originalDiagnostics.isEmpty());
      assertNotNull("No history error info for a failed job ", historyError);
      for (String diagString : originalDiagnostics) {
        assertTrue(historyError.contains(diagString));
      }
    } finally {
      LOG.info("FINISHED testCountersForFailedTask");
    }
  }

};