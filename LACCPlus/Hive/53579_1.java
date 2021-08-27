//,temp,MergeFileTask.java,79,212,temp,ColumnTruncateTask.java,79,218
//,3
public class xxx {
  @Override
  public int execute() {

    Context ctx = context;
    boolean ctxCreated = false;
    RunningJob rj = null;
    int returnVal = 0;

    try {
      if (ctx == null) {
        ctx = new Context(job);
        ctxCreated = true;
      }

      HiveFileFormatUtils.prepareJobOutput(job);
      job.setInputFormat(work.getInputformatClass());
      job.setOutputFormat(HiveOutputFormatImpl.class);
      job.setMapperClass(MergeFileMapper.class);
      job.setMapOutputKeyClass(NullWritable.class);
      job.setMapOutputValueClass(NullWritable.class);
      job.setOutputKeyClass(NullWritable.class);
      job.setOutputValueClass(NullWritable.class);
      job.setNumReduceTasks(0);
      // HIVE-23354 enforces that MR speculative execution is disabled
      job.setBoolean(MRJobConfig.REDUCE_SPECULATIVE, false);
      job.setBoolean(MRJobConfig.MAP_SPECULATIVE, false);

      // create the temp directories
      Path outputPath = work.getOutputDir();
      Path tempOutPath = Utilities.toTempPath(outputPath);
      FileSystem fs = tempOutPath.getFileSystem(job);
      if (!fs.exists(tempOutPath)) {
        fs.mkdirs(tempOutPath);
      }

      ExecDriver.propagateSplitSettings(job, work);

      // set job name
      boolean noName = StringUtils.isEmpty(job.get(MRJobConfig.JOB_NAME));

      String jobName = null;
      if (noName && this.getQueryPlan() != null) {
        int maxlen = conf.getIntVar(HiveConf.ConfVars.HIVEJOBNAMELENGTH);
        jobName = Utilities.abbreviate(this.getQueryPlan().getQueryStr(),
            maxlen - 6);
      }

      if (noName) {
        // This is for a special case to ensure unit tests pass
        job.set(MRJobConfig.JOB_NAME,
            jobName != null ? jobName
                : "JOB" + ThreadLocalRandom.current().nextInt());
      }

      // add input path
      addInputPaths(job, work);

      // serialize work
      Utilities.setMapWork(job, work, ctx.getMRTmpPath(), true);

      // remove pwd from conf file so that job tracker doesn't show this logs
      String pwd = HiveConf.getVar(job, HiveConf.ConfVars.METASTOREPWD);
      if (pwd != null) {
        HiveConf.setVar(job, HiveConf.ConfVars.METASTOREPWD, "HIVE");
      }

      // submit the job
      JobClient jc = new JobClient(job);

      // There is no need for Mergefile Task to add extra jars.

      // make this client wait if job trcker is not behaving well.
      Throttle.checkJobTracker(job, LOG);

      // Finally SUBMIT the JOB!
      rj = jc.submitJob(job);
      this.jobID = rj.getJobID();
      returnVal = jobExecHelper.progress(rj, jc, ctx);
      success = (returnVal == 0);

    } catch (Exception e) {
      setException(e);
      String mesg = " with exception '" + Utilities.getNameMessage(e) + "'";
      if (rj != null) {
        mesg = "Ended Job = " + rj.getJobID() + mesg;
      } else {
        mesg = "Job Submission failed" + mesg;
      }

      // Has to use full name to make sure it does not conflict with
      // org.apache.commons.lang3.StringUtils
      console.printError(mesg, "\n"
          + org.apache.hadoop.util.StringUtils.stringifyException(e));

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
        // get the list of Dynamic partition paths
        if (rj != null) {
          if (work.getAliasToWork() != null) {
            for (Operator<? extends OperatorDesc> op : work.getAliasToWork()
                .values()) {
              op.jobClose(job, success);
            }
          }
        }
      } catch (Exception e) {
	// jobClose needs to execute successfully otherwise fail task
	LOG.warn("Job close failed ",e);
        if (success) {
          setException(e);
          success = false;
          returnVal = 3;
          String mesg = "Job Commit failed with exception '" +
              Utilities.getNameMessage(e) + "'";
          console.printError(mesg, "\n" +
              org.apache.hadoop.util.StringUtils.stringifyException(e));
        }
      } finally {
	HadoopJobExecHelper.runningJobs.remove(rj);
      }
    }

    return returnVal;
  }

};