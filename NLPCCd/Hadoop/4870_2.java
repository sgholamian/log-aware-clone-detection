//,temp,sample_3516.java,2,16,temp,sample_1790.java,2,16
//,3
public class xxx {
public void dummy_method(){
Application application_1 = new Application("user_1", resourceManager);
application_1.submit();
application_1.addNodeManager(host_0, 1234, nm_0);
application_1.addNodeManager(host_1, 1234, nm_1);
Resource capability_1_0 = Resources.createResource(3 * GB);
application_1.addResourceRequestSpec(priority_1, capability_1_0);
Resource capability_1_1 = Resources.createResource(4 * GB);
application_1.addResourceRequestSpec(priority_0, capability_1_1);
Task task_1_0 = new Task(application_1, priority_1, new String[] {host_0, host_1});
application_1.addTask(task_1_0);


log.info("send resource requests to the scheduler");
}

};