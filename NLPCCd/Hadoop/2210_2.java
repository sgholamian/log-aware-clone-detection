//,temp,sample_1801.java,2,16,temp,sample_1799.java,2,16
//,3
public class xxx {
public void dummy_method(){
checkApplicationResourceUsage(2 * GB, application_0);
checkApplicationResourceUsage(0 * GB, application_1);
checkNodeResourceUsage(0*GB, nm_1);
application_0.finishTask(task_0_1);
application_0.schedule();
application_1.schedule();
nm_0.heartbeat();
nm_1.heartbeat();
checkApplicationResourceUsage(1 * GB, application_0);
checkApplicationResourceUsage(0 * GB, application_1);


log.info("finishing up task");
}

};