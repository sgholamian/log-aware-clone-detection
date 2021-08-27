//,temp,AtlasLoadTask.java,72,114,temp,AtlasDumpTask.java,83,130
//,3
public class xxx {
  @Override
  public int execute() {
    try {
      SecurityUtils.reloginExpiringKeytabUser();
      AtlasReplInfo atlasReplInfo  = createAtlasReplInfo();
      Map<String, Long> metricMap = new HashMap<>();
      metricMap.put(ReplUtils.MetricName.ENTITIES.name(), 0L);
      work.getMetricCollector().reportStageStart(getName(), metricMap);
      LOG.info("Loading atlas metadata from srcDb: {} to tgtDb: {} from staging: {}",
              atlasReplInfo.getSrcDB(), atlasReplInfo.getTgtDB(), atlasReplInfo.getStagingDir());
      AtlasLoadLogger replLogger = new AtlasLoadLogger(atlasReplInfo.getSrcDB(), atlasReplInfo.getTgtDB(),
              atlasReplInfo.getStagingDir().toString());
      replLogger.startLog();
      int importCount = importAtlasMetadata(atlasReplInfo);
      replLogger.endLog(importCount);
      work.getMetricCollector().reportStageProgress(getName(), ReplUtils.MetricName.ENTITIES.name(), importCount);
      LOG.info("Atlas entities import count {}", importCount);
      work.getMetricCollector().reportStageEnd(getName(), Status.SUCCESS);
      return 0;
    } catch (RuntimeException e) {
      LOG.error("RuntimeException while loading atlas metadata", e);
      setException(e);
      try{
        ReplUtils.handleException(true, e, work.getStagingDir().getParent().toString(), work.getMetricCollector(),
                getName(), conf);
      } catch (Exception ex){
        LOG.error("Failed to collect replication metrics: ", ex);
      }
      throw e;
    } catch (Exception e) {
      LOG.error("Exception while loading atlas metadata", e);
      setException(e);
      int errorCode = ErrorMsg.getErrorMsg(e.getMessage()).getErrorCode();
      try{
        return ReplUtils.handleException(true, e, work.getStagingDir().getParent().toString(), work.getMetricCollector(),
                getName(), conf);
      }
      catch (Exception ex){
        LOG.error("Failed to collect replication metrics: ", ex);
        return errorCode;
      }
    }
  }

};