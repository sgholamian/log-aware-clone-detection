//,temp,sample_3510.java,2,16,temp,sample_3512.java,2,16
//,3
public class xxx {
public void dummy_method(){
application_0.schedule();
application_1.schedule();
nodeUpdate(nm_0);
nodeUpdate(nm_1);
application_0.schedule();
checkApplicationResourceUsage(1 * GB, application_0);
application_1.schedule();
checkApplicationResourceUsage(3 * GB, application_1);
checkNodeResourceUsage(4*GB, nm_0);
checkNodeResourceUsage(0*GB, nm_1);


log.info("adding new tasks");
}

};