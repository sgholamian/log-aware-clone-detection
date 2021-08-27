//,temp,AtlasLoadTask.java,72,114,temp,AtlasDumpTask.java,83,130
//,3
public class xxx {
  @Override
  public int execute() {
    try {
      SecurityUtils.reloginExpiringKeytabUser();
      AtlasReplInfo atlasReplInfo = createAtlasReplInfo();
      LOG.info("Dumping Atlas metadata of srcDb: {}, for TgtDb: {} to staging location:",
              atlasReplInfo.getSrcDB(), atlasReplInfo.getTgtDB(), atlasReplInfo.getStagingDir());
      AtlasDumpLogger replLogger = new AtlasDumpLogger(atlasReplInfo.getSrcDB(),
              atlasReplInfo.getStagingDir().toString());
      replLogger.startLog();
      Map<String, Long> metricMap = new HashMap<>();
      metricMap.put(ReplUtils.MetricName.ENTITIES.name(), 0L);
      work.getMetricCollector().reportStageStart(getName(), metricMap);
      atlasRestClient = new AtlasRestClientBuilder(atlasReplInfo.getAtlasEndpoint())
              .getClient(atlasReplInfo.getConf());
      AtlasRequestBuilder atlasRequestBuilder = new AtlasRequestBuilder();
      String entityGuid = checkHiveEntityGuid(atlasRequestBuilder, atlasReplInfo.getSrcCluster(),
              atlasReplInfo.getSrcDB());
      long numBytesWritten = dumpAtlasMetaData(atlasRequestBuilder, atlasReplInfo);
      LOG.debug("Finished dumping atlas metadata, total:{} bytes written", numBytesWritten);
      long currentModifiedTime = getCurrentTimestamp(atlasReplInfo, entityGuid);
      createDumpMetadata(atlasReplInfo, currentModifiedTime);
      replLogger.endLog(0L);
      work.getMetricCollector().reportStageEnd(getName(), Status.SUCCESS);
      return 0;
    } catch (RuntimeException e) {
      LOG.error("RuntimeException while dumping atlas metadata", e);
      setException(e);
      try{
        ReplUtils.handleException(true, e, work.getStagingDir().getParent().toString(), work.getMetricCollector(),
                getName(), conf);
      } catch (Exception ex){
        LOG.error("Failed to collect replication metrics: ", ex);
      }
      throw e;
    } catch (Exception e) {
      LOG.error("Exception while dumping atlas metadata", e);
      setException(e);
      int errorCode = ErrorMsg.getErrorMsg(e.getMessage()).getErrorCode();
      try{
        return ReplUtils.handleException(true, e, work.getStagingDir().getParent().toString(), work.getMetricCollector(),
                getName(), conf);
      } catch (Exception ex) {
        LOG.error("Failed to collect replication metrics: ", ex);
        return errorCode;
      }
    }
  }

};