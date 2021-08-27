//,temp,sample_8345.java,2,17,temp,sample_8376.java,2,17
//,3
public class xxx {
public void dummy_method(){
Debugger debugger = getBeanForType(Debugger.class);
if (debugger != null) {
getContext().setDebugger(debugger);
}
UuidGenerator uuidGenerator = getBeanForType(UuidGenerator.class);
if (uuidGenerator != null) {
getContext().setUuidGenerator(uuidGenerator);
}
NodeIdFactory nodeIdFactory = getBeanForType(NodeIdFactory.class);
if (nodeIdFactory != null) {


log.info("using custom nodeidfactory");
}
}

};