//,temp,sample_2961.java,2,17,temp,sample_2966.java,2,16
//,3
public class xxx {
public void dummy_method(){
long startTime = Time.monotonicNow();
init(res);
TimelineReaderManager timelineReaderManager = getTimelineReaderManager();
Set<String> results = null;
try {
results = timelineReaderManager.getEntityTypes( TimelineReaderWebServicesUtils.createTimelineReaderContext( clusterId, userId, flowName, flowRunId, appId, null, null, null));
} catch (Exception e) {
handleException(e, url, startTime, "flowrunid");
}
long endTime = Time.monotonicNow();


log.info("processed url took ms");
}

};