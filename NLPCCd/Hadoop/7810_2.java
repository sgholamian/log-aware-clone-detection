//,temp,sample_1791.java,2,16,temp,sample_1802.java,2,16
//,3
public class xxx {
public void dummy_method(){
nm_1.heartbeat();
checkApplicationResourceUsage(0 * GB, application_0);
checkApplicationResourceUsage(3 * GB, application_1);
application_1.finishTask(task_1_1);
application_0.schedule();
application_1.schedule();
nm_0.heartbeat();
nm_1.heartbeat();
checkApplicationResourceUsage(0 * GB, application_0);
checkApplicationResourceUsage(3 * GB, application_1);


log.info("end testfifoscheduler");
}

};