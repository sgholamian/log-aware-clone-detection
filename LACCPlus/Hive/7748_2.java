//,temp,ReplicationMetricCollector.java,95,113,temp,ReplicationMetricCollector.java,73,93
//,3
public class xxx {
  public void reportStageEnd(String stageName, Status status, long lastReplId) throws SemanticException {
    if (isEnabled) {
      LOG.debug("Stage ended {}, {}, {}", stageName, status, lastReplId );
      Progress progress = replicationMetric.getProgress();
      Stage stage = progress.getStageByName(stageName);
      if(stage == null){
        stage = new Stage(stageName, status, -1L);
      }
      stage.setStatus(status);
      stage.setEndTime(System.currentTimeMillis());
      progress.addStage(stage);
      replicationMetric.setProgress(progress);
      Metadata metadata = replicationMetric.getMetadata();
      metadata.setLastReplId(lastReplId);
      replicationMetric.setMetadata(metadata);
      metricCollector.addMetric(replicationMetric);
      if (Status.FAILED == status || Status.FAILED_ADMIN == status) {
        reportEnd(status);
      }
    }
  }

};