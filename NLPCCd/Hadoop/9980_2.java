//,temp,sample_3517.java,2,16,temp,sample_1795.java,2,16
//,3
public class xxx {
public void dummy_method(){
nm_0.heartbeat();
nm_1.heartbeat();
application_0.schedule();
checkApplicationResourceUsage(3 * GB, application_0);
application_1.schedule();
checkApplicationResourceUsage(3 * GB, application_1);
nm_0.heartbeat();
nm_1.heartbeat();
checkNodeResourceUsage(4*GB, nm_0);
checkNodeResourceUsage(2*GB, nm_1);


log.info("finishing up task");
}

};