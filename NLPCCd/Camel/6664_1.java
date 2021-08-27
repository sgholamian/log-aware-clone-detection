//,temp,sample_7238.java,2,12,temp,sample_676.java,2,12
//,3
public class xxx {
protected void commitOffset(StateRepository<String, String> offsetRepository, TopicPartition partition, long partitionLastOffset) {
if (partitionLastOffset != -1) {
if (offsetRepository != null) {
offsetRepository.setState(serializeOffsetKey(partition), serializeOffsetValue(partitionLastOffset));
} else {


log.info("commitsync from topic with offset");
}
}
}

};