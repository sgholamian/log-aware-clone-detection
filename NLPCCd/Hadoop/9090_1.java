//,temp,sample_2634.java,2,12,temp,sample_6368.java,2,16
//,3
public class xxx {
private void putEntity(TimelineEntity entity) {
try {
if (LOG.isDebugEnabled()) {
}
client.putEntities(entity);
} catch (Exception e) {


log.info("error when publishing entity");
}
}

};