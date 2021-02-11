//,temp,sample_2970.java,2,16,temp,sample_2944.java,2,16
//,3
public class xxx {
public void dummy_method(){
TimelineEntity entity = null;
try {
entity = timelineReaderManager.getEntity( TimelineReaderWebServicesUtils.createTimelineReaderContext( clusterId, userId, flowName, flowRunId, appId, entityType, entityIdPrefix, entityId), TimelineReaderWebServicesUtils.createTimelineDataToRetrieve( confsToRetrieve, metricsToRetrieve, fields, metricsLimit, metricsTimeStart, metricsTimeEnd));
} catch (Exception e) {
handleException(e, url, startTime, "flowrunid");
}
long endTime = Time.monotonicNow();
if (entity == null) {
throw new NotFoundException("Timeline entity {id: " + entityId + ", type: " + entityType + " } is not found");
}


log.info("processed url took ms");
}

};