//,temp,sample_1798.java,2,16,temp,sample_3517.java,2,16
//,3
public class xxx {
public void dummy_method(){
application1.schedule();
checkNodeResourceUsage(3 * GB, nm0);
checkApplicationResourceUsage(0 * GB, application0);
checkApplicationResourceUsage(3 * GB, application1);
nodeUpdate(rm, nm0);
application0.schedule();
checkApplicationResourceUsage(1 * GB, application0);
application1.schedule();
checkApplicationResourceUsage(3 * GB, application1);
checkNodeResourceUsage(4 * GB, nm0);


log.info("start testnotassignmultiple");
}

};