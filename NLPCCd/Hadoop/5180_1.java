//,temp,sample_1794.java,2,16,temp,sample_3511.java,2,16
//,3
public class xxx {
public void dummy_method(){
application_1.schedule();
Task task_0_1 = new Task(application_0, priority_1, new String[] {host_0, host_1});
application_0.addTask(task_0_1);
Task task_0_2 = new Task(application_0, priority_1, new String[] {host_0, host_1});
application_0.addTask(task_0_2);
Task task_0_3 = new Task(application_0, priority_0, new String[] {ResourceRequest.ANY});
application_0.addTask(task_0_3);
application_0.schedule();
nm_0.heartbeat();
nm_1.heartbeat();


log.info("trying to allocate");
}

};