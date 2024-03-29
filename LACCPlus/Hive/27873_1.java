//,temp,LocalSparkJobMonitor.java,40,141,temp,RemoteSparkJobMonitor.java,54,192
//,3
public class xxx {
  public int startMonitor() {
    boolean running = false;
    boolean done = false;
    int rc = 0;
    JobExecutionStatus lastState = null;
    Map<SparkStage, SparkStageProgress> lastProgressMap = null;

    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.SPARK_RUN_JOB);
    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.SPARK_SUBMIT_TO_RUNNING);

    startTime = System.currentTimeMillis();

    while (true) {
      try {
        JobExecutionStatus state = sparkJobStatus.getState();
        if (LOG.isDebugEnabled()) {
          console.printInfo("state = " + state);
        }

        if (state == null) {
          long timeCount = (System.currentTimeMillis() - startTime)/1000;
          if (timeCount > monitorTimeoutInterval) {
            console.printError("Job hasn't been submitted after " + timeCount + "s. Aborting it.");
            console.printError("Status: " + state);
            running = false;
            done = true;
            rc = 2;
            break;
          }
        } else if (state != lastState || state == JobExecutionStatus.RUNNING) {
          lastState = state;
          Map<SparkStage, SparkStageProgress> progressMap = sparkJobStatus.getSparkStageProgress();

          switch (state) {
          case RUNNING:
            if (!running) {
              perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.SPARK_SUBMIT_TO_RUNNING);
              // print job stages.
              console.printInfo("\nQuery Hive on Spark job["
                + sparkJobStatus.getJobId() + "] stages:");
              for (int stageId : sparkJobStatus.getStageIds()) {
                console.printInfo(Integer.toString(stageId));
              }

              console.printInfo("\nStatus: Running (Hive on Spark job["
                + sparkJobStatus.getJobId() + "])");
              running = true;

              console.printInfo("Job Progress Format\nCurrentTime StageId_StageAttemptId: "
                + "SucceededTasksCount(+RunningTasksCount-FailedTasksCount)/TotalTasksCount [StageCost]");
            }

            updateFunction.printStatus(progressMap, lastProgressMap);
            lastProgressMap = progressMap;
            break;
          case SUCCEEDED:
            updateFunction.printStatus(progressMap, lastProgressMap);
            lastProgressMap = progressMap;
            double duration = (System.currentTimeMillis() - startTime) / 1000.0;
            console.printInfo("Status: Finished successfully in "
              + String.format("%.2f seconds", duration));
            running = false;
            done = true;
            break;
          case FAILED:
            console.printError("Status: Failed");
            running = false;
            done = true;
            rc = 3;
            break;
          case UNKNOWN:
            console.printError("Status: Unknown");
            running = false;
            done = true;
            rc = 4;
            break;
          }
        }
        if (!done) {
          Thread.sleep(checkInterval);
        }
      } catch (Exception e) {
        String msg = " with exception '" + Utilities.getNameMessage(e) + "'";
        msg = "Failed to monitor Job[ " + sparkJobStatus.getJobId() + "]" + msg;

        // Has to use full name to make sure it does not conflict with
        // org.apache.commons.lang3.StringUtils
        LOG.error(msg, e);
        console.printError(msg, "\n" + org.apache.hadoop.util.StringUtils.stringifyException(e));
        rc = 1;
        done = true;
        sparkJobStatus.setMonitorError(e);
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