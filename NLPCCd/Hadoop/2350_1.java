//,temp,sample_2943.java,2,17,temp,sample_2938.java,2,16
//,3
public class xxx {
public void dummy_method(){
init(res);
TimelineReaderManager timelineReaderManager = getTimelineReaderManager();
TimelineEntity entity = null;
try {
entity = timelineReaderManager.getEntity( TimelineReaderWebServicesUtils.createTimelineReaderContext( clusterId, userId, flowName, flowRunId, appId, entityType, entityIdPrefix, entityId), TimelineReaderWebServicesUtils.createTimelineDataToRetrieve( confsToRetrieve, metricsToRetrieve, fields, metricsLimit, metricsTimeStart, metricsTimeEnd));
} catch (Exception e) {
handleException(e, url, startTime, "flowrunid");
}
long endTime = Time.monotonicNow();
if (entity == null) {


log.info("processed url but entity not found took ms");
}
}

};