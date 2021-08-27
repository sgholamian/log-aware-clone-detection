//,temp,sample_6324.java,2,15,temp,sample_6325.java,2,20
//,3
public class xxx {
public void dummy_method(){
if (isStarting() || isStarted()) {
com.google.common.base.Optional<Value> value = consulResponse.getResponse();
if (value.isPresent()) {
com.google.common.base.Optional<String> sid = value.get().getSession();
if (!sid.isPresent()) {
boolean lock = acquireLock();
localMember.setMaster(lock);
} else {
boolean master = sid.get().equals(sessionId.get());
if (!master) {


log.info("path is held by session local session is");
}
}
}
}
}

};