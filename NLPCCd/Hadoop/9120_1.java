//,temp,sample_2634.java,2,12,temp,sample_5814.java,2,8
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