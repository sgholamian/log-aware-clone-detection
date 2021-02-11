//,temp,sample_1796.java,2,16,temp,sample_3520.java,2,16
//,3
public class xxx {
public void dummy_method(){
checkNodeResourceUsage(2*GB, nm_1);
application_0.finishTask(task_0_0);
application_0.schedule();
application_1.schedule();
nm_0.heartbeat();
nm_1.heartbeat();
checkApplicationResourceUsage(3 * GB, application_0);
checkApplicationResourceUsage(3 * GB, application_1);
checkNodeResourceUsage(4*GB, nm_0);
checkNodeResourceUsage(2*GB, nm_1);


log.info("finishing up task");
}

};