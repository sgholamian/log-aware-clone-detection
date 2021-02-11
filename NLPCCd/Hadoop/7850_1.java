//,temp,sample_74.java,2,16,temp,sample_3512.java,2,16
//,3
public class xxx {
public void dummy_method(){
Task t3 = new Task(application, priority0, new String[] {ResourceRequest.ANY});
application.addTask(t3);
application.schedule();
checkResourceUsage(nm1, nm2);
nodeUpdate(nm2);
nodeUpdate(nm2);
nodeUpdate(nm1);
nodeUpdate(nm1);
application.schedule();
checkResourceUsage(nm1, nm2);


log.info("finishing up tasks");
}

};