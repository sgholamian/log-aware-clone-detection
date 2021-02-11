//,temp,sample_2970.java,2,16,temp,sample_2944.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
TimelineReaderContext context = TimelineReaderWebServicesUtils .createTimelineReaderContext(clusterId, null, null, null, null, entityType, entityIdPrefix, entityId, userId);
entities = timelineReaderManager.getEntities(context, new TimelineEntityFilters.Builder().build(), TimelineReaderWebServicesUtils.createTimelineDataToRetrieve( confsToRetrieve, metricsToRetrieve, fields, metricsLimit, metricsTimeStart, metricsTimeEnd));
} catch (Exception e) {
handleException(e, url, startTime, "");
}
long endTime = Time.monotonicNow();
if (entities == null) {
entities = Collections.emptySet();
}


log.info("processed url took ms");
}

};