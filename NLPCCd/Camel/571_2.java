//,temp,sample_7091.java,2,8,temp,sample_675.java,2,10
//,3
public class xxx {
private void commitOffset(StateRepository<String, String> offsetRepository, TopicPartition partition, long partitionLastOffset, boolean forceCommit) {
if (partitionLastOffset != -1) {
if (offsetRepository != null) {


log.info("saving offset repository state from topic with offset");
}
}
}

};