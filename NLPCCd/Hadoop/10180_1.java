//,temp,sample_2961.java,2,17,temp,sample_2966.java,2,16
//,3
public class xxx {
public void dummy_method(){
init(res);
TimelineReaderManager timelineReaderManager = getTimelineReaderManager();
TimelineEntity entity = null;
try {
entity = timelineReaderManager.getEntity( TimelineReaderWebServicesUtils.createTimelineReaderContext( clusterId, userId, flowName, flowRunId, appId, TimelineEntityType.YARN_APPLICATION.toString(), null, null), TimelineReaderWebServicesUtils.createTimelineDataToRetrieve( confsToRetrieve, metricsToRetrieve, fields, metricsLimit, metricsTimeStart, metricsTimeEnd));
} catch (Exception e) {
handleException(e, url, startTime, "flowrunid");
}
long endTime = Time.monotonicNow();
if (entity == null) {


log.info("processed url but app not found took ms");
}
}

};