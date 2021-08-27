//,temp,RemoteSparkJobStatus.java,306,330,temp,RemoteSparkJobStatus.java,260,290
//,3
public class xxx {
    @Override
    public ArrayList<SparkStageInfo> call(JobContext jc) throws Exception {
      SparkJobInfo jobInfo = jc.sc().statusTracker().getJobInfo(sparkJobId);
      if (jobInfo == null) {
        ArrayList<JavaFutureAction<?>> list = new ArrayList<>(jc.getMonitoredJobs().get(clientJobId));
        if (list != null && list.size() == 1) {
          JavaFutureAction<?> futureAction = list.get(0);
          if (futureAction.isDone()) {
            boolean futureSucceed = true;
            try {
              futureAction.get();
            } catch (Exception e) {
              LOG.error("Failed to run job " + sparkJobId, e);
              futureSucceed = false;
            }
            jobInfo = getDefaultJobInfo(sparkJobId,
                futureSucceed ? JobExecutionStatus.SUCCEEDED : JobExecutionStatus.FAILED);
          }
        }
      }
      if (jobInfo == null) {
        jobInfo = getDefaultJobInfo(sparkJobId, JobExecutionStatus.UNKNOWN);
      }
      ArrayList<SparkStageInfo> sparkStageInfos = new ArrayList<>();
      int[] stageIds = jobInfo.stageIds();
      for(Integer stageid : stageIds) {
        SparkStageInfo stageInfo = jc.sc().statusTracker().getStageInfo(stageid);
        sparkStageInfos.add(stageInfo);
      }
      return sparkStageInfos;
    }

};