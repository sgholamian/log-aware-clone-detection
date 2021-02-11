//,temp,sample_2943.java,2,17,temp,sample_2938.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
TimelineReaderContext context = TimelineReaderWebServicesUtils .createTimelineReaderContext(clusterId, userId, flowName, flowRunId, appId, entityType, null, null);
entities = timelineReaderManager.getEntities(context, TimelineReaderWebServicesUtils.createTimelineEntityFilters( limit, createdTimeStart, createdTimeEnd, relatesTo, isRelatedTo, infofilters, conffilters, metricfilters, eventfilters, fromId), TimelineReaderWebServicesUtils.createTimelineDataToRetrieve( confsToRetrieve, metricsToRetrieve, fields, metricsLimit, metricsTimeStart, metricsTimeEnd));
} catch (Exception e) {
handleException(e, url, startTime, "createdTime start/end or limit or flowrunid");
}
long endTime = Time.monotonicNow();
if (entities == null) {
entities = Collections.emptySet();
}


log.info("processed url took ms");
}

};