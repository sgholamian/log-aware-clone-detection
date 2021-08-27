//,temp,sample_7238.java,2,12,temp,sample_676.java,2,12
//,3
public class xxx {
private void commitOffset(StateRepository<String, String> offsetRepository, TopicPartition partition, long partitionLastOffset, boolean forceCommit) {
if (partitionLastOffset != -1) {
if (offsetRepository != null) {
offsetRepository.setState(serializeOffsetKey(partition), serializeOffsetValue(partitionLastOffset));
} else if (forceCommit) {


log.info("forcing commitsync from topic with offset");
}
}
}

};