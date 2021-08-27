//,temp,sample_8339.java,2,17,temp,sample_8377.java,2,17
//,3
public class xxx {
public void dummy_method(){
UuidGenerator uuidGenerator = getBeanForType(UuidGenerator.class);
if (uuidGenerator != null) {
getContext().setUuidGenerator(uuidGenerator);
}
NodeIdFactory nodeIdFactory = getBeanForType(NodeIdFactory.class);
if (nodeIdFactory != null) {
getContext().setNodeIdFactory(nodeIdFactory);
}
StreamCachingStrategy streamCachingStrategy = getBeanForType(StreamCachingStrategy.class);
if (streamCachingStrategy != null) {


log.info("using custom streamcachingstrategy");
}
}

};