//,temp,JobHistoryFileReplayMapperV2.java,51,141,temp,JobHistoryFileReplayMapperV1.java,53,128
//,3
public class xxx {
  public void map(IntWritable key, IntWritable val, Context context) throws IOException {
    // collect the apps it needs to process
    TimelineClient tlc = TimelineClient.createTimelineClient();
    TimelineEntityConverterV1 converter = new TimelineEntityConverterV1();
    JobHistoryFileReplayHelper helper = new JobHistoryFileReplayHelper(context);
    int replayMode = helper.getReplayMode();
    Collection<JobFiles> jobs =
        helper.getJobFiles();
    JobHistoryFileParser parser = helper.getParser();

    if (jobs.isEmpty()) {
      LOG.info(context.getTaskAttemptID().getTaskID() +
          " will process no jobs");
    } else {
      LOG.info(context.getTaskAttemptID().getTaskID() + " will process " +
          jobs.size() + " jobs");
    }
    for (JobFiles job: jobs) {
      // process each job
      String jobIdStr = job.getJobId();
      LOG.info("processing " + jobIdStr + "...");
      JobId jobId = TypeConverter.toYarn(JobID.forName(jobIdStr));
      ApplicationId appId = jobId.getAppId();

      try {
        // parse the job info and configuration
        Path historyFilePath = job.getJobHistoryFilePath();
        Path confFilePath = job.getJobConfFilePath();
        if ((historyFilePath == null) || (confFilePath == null)) {
          continue;
        }
        JobInfo jobInfo =
            parser.parseHistoryFile(historyFilePath);
        Configuration jobConf =
            parser.parseConfiguration(confFilePath);
        LOG.info("parsed the job history file and the configuration file for job "
            + jobIdStr);

        // create entities from job history and write them
        long totalTime = 0;
        Set<TimelineEntity> entitySet =
            converter.createTimelineEntities(jobInfo, jobConf);
        LOG.info("converted them into timeline entities for job " + jobIdStr);
        // use the current user for this purpose
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        long startWrite = System.nanoTime();
        try {
          switch (replayMode) {
          case JobHistoryFileReplayHelper.WRITE_ALL_AT_ONCE:
            writeAllEntities(tlc, entitySet, ugi);
            break;
          case JobHistoryFileReplayHelper.WRITE_PER_ENTITY:
            writePerEntity(tlc, entitySet, ugi);
            break;
          default:
            break;
          }
        } catch (Exception e) {
          context.getCounter(PerfCounters.TIMELINE_SERVICE_WRITE_FAILURES).
              increment(1);
          LOG.error("writing to the timeline service failed", e);
        }
        long endWrite = System.nanoTime();
        totalTime += TimeUnit.NANOSECONDS.toMillis(endWrite-startWrite);
        int numEntities = entitySet.size();
        LOG.info("wrote " + numEntities + " entities in " + totalTime + " ms");

        context.getCounter(PerfCounters.TIMELINE_SERVICE_WRITE_TIME).
            increment(totalTime);
        context.getCounter(PerfCounters.TIMELINE_SERVICE_WRITE_COUNTER).
            increment(numEntities);
      } finally {
        context.progress(); // move it along
      }
    }
  }

};