//,temp,sample_3511.java,2,16,temp,sample_1795.java,2,16
//,3
public class xxx {
public void dummy_method(){
application_1.schedule();
checkApplicationResourceUsage(3 * GB, application_1);
checkNodeResourceUsage(4*GB, nm_0);
checkNodeResourceUsage(0*GB, nm_1);
Task task_1_1 = new Task(application_1, priority_0, new String[] {ResourceRequest.ANY});
application_1.addTask(task_1_1);
application_1.schedule();
Task task_0_1 = new Task(application_0, priority_0, new String[] {host_0, host_1});
application_0.addTask(task_0_1);
application_0.schedule();


log.info("sending hb from");
}

};