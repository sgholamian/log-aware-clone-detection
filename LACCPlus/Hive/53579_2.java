//,temp,MergeFileTask.java,79,212,temp,ColumnTruncateTask.java,79,218
//,3
public class xxx {
  @Override
  public int execute() {
    HiveConf.setVar(job, HiveConf.ConfVars.HIVEINPUTFORMAT,
        BucketizedHiveInputFormat.class.getName());
    success = true;
    HiveFileFormatUtils.prepareJobOutput(job);
    job.setOutputFormat(HiveOutputFormatImpl.class);
    job.setMapperClass(work.getMapperClass());

    Context ctx = context;
    boolean ctxCreated = false;
    if (ctx == null) {
      ctx = new Context(job);
      ctxCreated = true;
    }

    job.setMapOutputKeyClass(NullWritable.class);
    job.setMapOutputValueClass(NullWritable.class);
    if(work.getNumMapTasks() != null) {
      job.setNumMapTasks(work.getNumMapTasks());
    }

    // zero reducers
    job.setNumReduceTasks(0);
    // HIVE-23354 enforces that MR speculative execution is disabled
    job.setBoolean(MRJobConfig.REDUCE_SPECULATIVE, false);
    job.setBoolean(MRJobConfig.MAP_SPECULATIVE, false);

    if (work.getMinSplitSize() != null) {
      HiveConf.setLongVar(job, HiveConf.ConfVars.MAPREDMINSPLITSIZE, work
          .getMinSplitSize().longValue());
    }

    if (work.getInputformat() != null) {
      HiveConf.setVar(job, HiveConf.ConfVars.HIVEINPUTFORMAT, work
          .getInputformat());
    }

    String inpFormat = HiveConf.getVar(job, HiveConf.ConfVars.HIVEINPUTFORMAT);
    LOG.info("Using " + inpFormat);

    try {
      job.setInputFormat(JavaUtils.loadClass(inpFormat));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    Path outputPath = this.work.getOutputDir();
    Path tempOutPath = Utilities.toTempPath(outputPath);
    try {
      FileSystem fs = tempOutPath.getFileSystem(job);
      if (!fs.exists(tempOutPath)) {
        fs.mkdirs(tempOutPath);
      }
    } catch (IOException e) {
      setException(e);
      LOG.error("Can't make path " + outputPath, e);
      return 6;
    }

    job.setOutputKeyClass(NullWritable.class);
    job.setOutputValueClass(NullWritable.class);

    int returnVal = 0;
    RunningJob rj = null;

    boolean noName = StringUtils.isEmpty(job.get(MRJobConfig.JOB_NAME));

    String jobName = null;
    if (noName && this.getQueryPlan() != null) {
      int maxlen = conf.getIntVar(HiveConf.ConfVars.HIVEJOBNAMELENGTH);
      jobName = Utilities.abbreviate(this.getQueryPlan().getQueryStr(),
          maxlen - 6);
    }

    if (noName) {
      // This is for a special case to ensure unit tests pass
      job.set(MRJobConfig.JOB_NAME, jobName != null ? jobName
          : "JOB" + ThreadLocalRandom.current().nextInt());
    }

    try {
      addInputPaths(job, work);

      MapredWork mrWork = new MapredWork();
      mrWork.setMapWork(work);
      Utilities.setMapRedWork(job, mrWork, ctx.getMRTmpPath());

      // remove the pwd from conf file so that job tracker doesn't show this
      // logs
      String pwd = HiveConf.getVar(job, HiveConf.ConfVars.METASTOREPWD);
      if (pwd != null) {
        HiveConf.setVar(job, HiveConf.ConfVars.METASTOREPWD, "HIVE");
      }
      JobClient jc = new JobClient(job);

      String addedJars = Utilities.getResourceFiles(job, SessionState.ResourceType.JAR);
      if (!addedJars.isEmpty()) {
        job.set("tmpjars", addedJars);
      }

      // make this client wait if job trcker is not behaving well.
      Throttle.checkJobTracker(job, LOG);

      // Finally SUBMIT the JOB!
      rj = jc.submitJob(job);
      this.jobID = rj.getJobID();
      returnVal = jobExecHelper.progress(rj, jc, ctx);
      success = (returnVal == 0);

    } catch (Exception e) {
      String mesg = rj != null ? ("Ended Job = " + rj.getJobID()) : "Job Submission failed";
      // Has to use full name to make sure it does not conflict with
      // org.apache.commons.lang3.StringUtils
      LOG.error(mesg, e);
      setException(e);

      success = false;
      returnVal = 1;
    } finally {
      try {
        if (ctxCreated) {
          ctx.clear();
        }
        if (rj != null) {
          if (returnVal != 0) {
            rj.killJob();
          }
        }
        ColumnTruncateMapper.jobClose(outputPath, success, job, console,
          work.getDynPartCtx(), null);
      } catch (Exception e) {
        LOG.warn("Failed while cleaning up ", e);
      } finally {
        HadoopJobExecHelper.runningJobs.remove(rj);
      }
    }

    return (returnVal);
  }

};