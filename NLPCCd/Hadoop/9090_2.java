//,temp,sample_2634.java,2,12,temp,sample_6368.java,2,16
//,3
public class xxx {
private void putEntity(TimelineEntity entity, ApplicationId appId) {
try {
if (LOG.isDebugEnabled()) {
}
TimelineV2Client timelineClient = getTimelineClient(appId);
if (timelineClient != null) {
timelineClient.putEntities(entity);
} else {
}
} catch (Exception e) {


log.info("error when publishing entity");
}
}

};