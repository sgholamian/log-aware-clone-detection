//,temp,sample_6324.java,2,15,temp,sample_6325.java,2,20
//,3
public class xxx {
public void onComplete(ConsulResponse<com.google.common.base.Optional<Value>> consulResponse) {
if (isStarting() || isStarted()) {
com.google.common.base.Optional<Value> value = consulResponse.getResponse();
if (value.isPresent()) {
com.google.common.base.Optional<String> sid = value.get().getSession();
if (!sid.isPresent()) {
boolean lock = acquireLock();


log.info("try to acquire lock on path with id result");
}
}
}
}

};