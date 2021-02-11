//,temp,sample_3514.java,2,16,temp,sample_1795.java,2,16
//,3
public class xxx {
public void dummy_method(){
nodeUpdate(nm_0);
nodeUpdate(nm_1);
application_0.schedule();
checkApplicationResourceUsage(1 * GB, application_0);
application_1.schedule();
checkApplicationResourceUsage(5 * GB, application_1);
nodeUpdate(nm_0);
nodeUpdate(nm_1);
checkNodeResourceUsage(4*GB, nm_0);
checkNodeResourceUsage(2*GB, nm_1);


log.info("end testcapacityscheduler");
}

};