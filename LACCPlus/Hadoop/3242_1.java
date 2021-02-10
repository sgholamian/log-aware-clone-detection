//,temp,JobHistoryFileReplayMapperV2.java,51,141,temp,JobHistoryFileReplayMapperV1.java,53,128
//,3
public class xxx {
  @Override
  protected void writeEntities(Configuration tlConf,
      TimelineCollectorManager manager, Context context) throws IOException {
    JobHistoryFileReplayHelper helper = new JobHistoryFileReplayHelper(context);
    int replayMode = helper.getReplayMode();
    JobHistoryFileParser parser = helper.getParser();
    TimelineEntityConverterV2 converter = new TimelineEntityConverterV2();

    // collect the apps it needs to process
    Collection<JobFiles> jobs = helper.getJobFiles();
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
      // skip if either of the file is missing
      if (job.getJobConfFilePath() == null ||
          job.getJobHistoryFilePath() == null) {
        LOG.info(jobIdStr + " missing either the job history file or the " +
            "configuration file. Skipping.");
        continue;
      }
      LOG.info("processing " + jobIdStr + "...");
      JobId jobId = TypeConverter.toYarn(JobID.forName(jobIdStr));
      ApplicationId appId = jobId.getAppId();

      // create the app level timeline collector and start it
      AppLevelTimelineCollector collector =
          new AppLevelTimelineCollector(appId);
      manager.putIfAbsent(appId, collector);
      try {
        // parse the job info and configuration
        JobInfo jobInfo =
            parser.parseHistoryFile(job.getJobHistoryFilePath());
        Configuration jobConf =
            parser.parseConfiguration(job.getJobConfFilePath());
        LOG.info("parsed the job history file and the configuration file " +
            "for job " + jobIdStr);

        // set the context
        // flow id: job name, flow run id: timestamp, user id
        TimelineCollectorContext tlContext =
            collector.getTimelineEntityContext();
        tlContext.setFlowName(jobInfo.getJobname());
        tlContext.setFlowRunId(jobInfo.getSubmitTime());
        tlContext.setUserId(jobInfo.getUsername());

        // create entities from job history and write them
        long totalTime = 0;
        List<TimelineEntity> entitySet =
            converter.createTimelineEntities(jobInfo, jobConf);
        LOG.info("converted them into timeline entities for job " + jobIdStr);
        // use the current user for this purpose
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        long startWrite = System.nanoTime();
        try {
          switch (replayMode) {
          case JobHistoryFileReplayHelper.WRITE_ALL_AT_ONCE:
            writeAllEntities(collector, entitySet, ugi);
            break;
          case JobHistoryFileReplayHelper.WRITE_PER_ENTITY:
            writePerEntity(collector, entitySet, ugi);
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
        manager.remove(appId);
        context.progress(); // move it along
      }
    }
  }

};