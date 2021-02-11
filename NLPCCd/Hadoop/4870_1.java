//,temp,sample_3516.java,2,16,temp,sample_1790.java,2,16
//,3
public class xxx {
public void dummy_method(){
Resource capability10 = Resources.createResource(3 * GB, 1);
application1.addResourceRequestSpec(priority0, capability10);
Resource capability11 = Resources.createResource(4 * GB, 1);
application1.addResourceRequestSpec(priority1, capability11);
Task task10 = new Task(application1, priority0, new String[] {host0});
Task task11 = new Task(application1, priority1, new String[] {host0});
application1.addTask(task10);
application1.addTask(task11);
application0.schedule();
application1.schedule();


log.info("kick");
}

};