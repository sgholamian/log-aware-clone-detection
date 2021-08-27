//,temp,sample_6930.java,2,11,temp,sample_6929.java,2,11
//,3
public class xxx {
public synchronized Handle register() throws Exception {
final HandleImplementation handle = new HandleImplementation();
final boolean needStart = this.handles.isEmpty();
this.handles.add(handle);
if (needStart) {


log.info("calling performstart");
}
}

};