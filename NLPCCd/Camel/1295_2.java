//,temp,sample_8374.java,2,17,temp,sample_8378.java,2,17
//,3
public class xxx {
public void dummy_method(){
NodeIdFactory nodeIdFactory = getBeanForType(NodeIdFactory.class);
if (nodeIdFactory != null) {
getContext().setNodeIdFactory(nodeIdFactory);
}
StreamCachingStrategy streamCachingStrategy = getBeanForType(StreamCachingStrategy.class);
if (streamCachingStrategy != null) {
getContext().setStreamCachingStrategy(streamCachingStrategy);
}
MessageHistoryFactory messageHistoryFactory = getBeanForType(MessageHistoryFactory.class);
if (messageHistoryFactory != null) {


log.info("using custom messagehistoryfactory");
}
}

};