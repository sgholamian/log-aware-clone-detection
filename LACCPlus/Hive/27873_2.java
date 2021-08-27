//,temp,LocalSparkJobMonitor.java,40,141,temp,RemoteSparkJobMonitor.java,54,192
//,3
public class xxx {
  @Override
  public int startMonitor() {
    boolean running = false;
    boolean done = false;
    int rc = 0;
    Map<SparkStage, SparkStageProgress> lastProgressMap = null;

    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.SPARK_RUN_JOB);
    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.SPARK_SUBMIT_TO_RUNNING);

    startTime = System.currentTimeMillis();
    JobHandle.State state = null;

    while (true) {
      try {
        state = sparkJobStatus.getRemoteJobState();
        Preconditions.checkState(sparkJobStatus.isRemoteActive(), "Connection to remote Spark driver was lost");

        switch (state) {
        case SENT:
        case QUEUED:
          long timeCount = (System.currentTimeMillis() - startTime) / 1000;
          if ((timeCount > monitorTimeoutInterval)) {
            HiveException he = new HiveException(ErrorMsg.SPARK_JOB_MONITOR_TIMEOUT,
                Long.toString(timeCount));
            sparkJobStatus.setMonitorError(he);
            running = false;
            done = true;
            rc = 2;
          }
          if (LOG.isDebugEnabled()) {
            console.printInfo("Spark job state = " + state );
          }
          break;
        case STARTED:
          JobExecutionStatus sparkJobState = sparkJobStatus.getState();
          if (sparkJobState == JobExecutionStatus.RUNNING) {
            Map<SparkStage, SparkStageProgress> progressMap = sparkJobStatus.getSparkStageProgress();
            if (!running) {
              perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.SPARK_SUBMIT_TO_RUNNING);
              printAppInfo();
              console.printInfo("Hive on Spark Session Web UI URL: " + sparkJobStatus.getWebUIURL());
              // print job stages.
              console.printInfo("\nQuery Hive on Spark job[" + sparkJobStatus.getJobId() +
                  "] stages: " + Arrays.toString(sparkJobStatus.getStageIds()));

              console.printInfo("Spark job[" + sparkJobStatus.getJobId() + "] status = RUNNING");
              running = true;

              String format = "Job Progress Format\nCurrentTime StageId_StageAttemptId: "
                  + "SucceededTasksCount(+RunningTasksCount-FailedTasksCount)/TotalTasksCount";
              if (!inPlaceUpdate) {
                console.printInfo(format);
              } else {
                console.logInfo(format);
              }
            } else {
              // Get the maximum of the number of tasks in the stages of the job and cancel the job if it goes beyond the limit.
              if (sparkStageMaxTaskCount != -1 && stageMaxTaskCount == 0) {
                stageMaxTaskCount = getStageMaxTaskCount(progressMap);
                if (stageMaxTaskCount > sparkStageMaxTaskCount) {
                  rc = 4;
                  done = true;
                  console.printInfo("\nThe number of task in one stage of the Spark job [" + stageMaxTaskCount + "] is greater than the limit [" +
                      sparkStageMaxTaskCount + "]. The Spark job will be cancelled.");
                }
              }

              // Count the number of tasks, and kill application if it goes beyond the limit.
              if (sparkJobMaxTaskCount != -1 && totalTaskCount == 0) {
                totalTaskCount = getTotalTaskCount(progressMap);
                if (totalTaskCount > sparkJobMaxTaskCount) {
                  rc = 4;
                  done = true;
                  console.printInfo("\nThe total number of task in the Spark job [" + totalTaskCount + "] is greater than the limit [" +
                      sparkJobMaxTaskCount + "]. The Spark job will be cancelled.");
                }
              }
            }

            updateFunction.printStatus(progressMap, lastProgressMap);
            lastProgressMap = progressMap;
          }
          break;
        case SUCCEEDED:
          Map<SparkStage, SparkStageProgress> progressMap = sparkJobStatus.getSparkStageProgress();
          updateFunction.printStatus(progressMap, lastProgressMap);
          lastProgressMap = progressMap;
          double duration = (System.currentTimeMillis() - startTime) / 1000.0;
          console.printInfo("Spark job[" + sparkJobStatus.getJobId() + "] finished successfully in "
            + String.format("%.2f second(s)", duration));
          running = false;
          done = true;
          break;
        case FAILED:
          LOG.error("Spark job[" + sparkJobStatus.getJobId() + "] failed", sparkJobStatus.getSparkJobException());
          running = false;
          done = true;
          rc = 3;
          break;
        case CANCELLED:
          console.printInfo("Spark job[" + sparkJobStatus.getJobId() + " was cancelled");
          running = false;
          done = true;
          rc = 3;
          break;
        }

        if (!done) {
          Thread.sleep(checkInterval);
        }
      } catch (Exception e) {
        Exception finalException = e;
        if (e instanceof InterruptedException ||
                (e instanceof HiveException && e.getCause() instanceof InterruptedException)) {
          finalException = new HiveException(e, ErrorMsg.SPARK_JOB_INTERRUPTED);
          LOG.warn("Interrupted while monitoring the Hive on Spark application, exiting");
        } else {
          String msg = " with exception '" + Utilities.getNameMessage(e) + "' Last known state = " +
                  (state != null ? state.name() : "UNKNOWN");
          msg = "Failed to monitor Job[" + sparkJobStatus.getJobId() + "]" + msg;

          // Has to use full name to make sure it does not conflict with
          // org.apache.commons.lang3.StringUtils
          console.printError(msg, "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
        }
        rc = 1;
        done = true;
        sparkJobStatus.setMonitorError(finalException);
      } finally {
        if (done) {
          break;
        }
      }
    }

    perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.SPARK_RUN_JOB);
    return rc;
  }

};