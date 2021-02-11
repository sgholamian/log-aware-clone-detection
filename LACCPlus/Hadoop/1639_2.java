//,temp,TestJobHistoryParsing.java,524,581,temp,TestJobHistoryParsing.java,396,459
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testHistoryParsingForFailedAttempts() throws Exception {
    LOG.info("STARTING testHistoryParsingForFailedAttempts");
    try {
      Configuration conf = new Configuration();
      conf.setClass(
          NET_TOPOLOGY_NODE_SWITCH_MAPPING_IMPL_KEY,
          MyResolver.class, DNSToSwitchMapping.class);
      RackResolver.init(conf);
      MRApp app = new MRAppWithHistoryWithFailedAttempt(2, 1, true, this
          .getClass().getName(), true);
      app.submit(conf);
      Job job = app.getContext().getAllJobs().values().iterator().next();
      JobId jobId = job.getID();
      app.waitForState(job, JobState.SUCCEEDED);

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
      int noOffailedAttempts = 0;
      Map<TaskID, TaskInfo> allTasks = jobInfo.getAllTasks();
      for (Task task : job.getTasks().values()) {
        TaskInfo taskInfo = allTasks.get(TypeConverter.fromYarn(task.getID()));
        for (TaskAttempt taskAttempt : task.getAttempts().values()) {
          TaskAttemptInfo taskAttemptInfo = taskInfo.getAllTaskAttempts().get(
              TypeConverter.fromYarn((taskAttempt.getID())));
          // Verify rack-name for all task attempts
          Assert.assertEquals("rack-name is incorrect",
              taskAttemptInfo.getRackname(), RACK_NAME);
          if (taskAttemptInfo.getTaskStatus().equals("FAILED")) {
            noOffailedAttempts++;
          }
        }
      }
      Assert.assertEquals("No of Failed tasks doesn't match.", 2,
          noOffailedAttempts);
    } finally {
      LOG.info("FINISHED testHistoryParsingForFailedAttempts");
    }
  }

};