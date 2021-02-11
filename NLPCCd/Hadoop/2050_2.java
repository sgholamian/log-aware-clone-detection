//,temp,sample_1796.java,2,16,temp,sample_3520.java,2,16
//,3
public class xxx {
public void dummy_method(){
application1.schedule();
checkNodeResourceUsage(4 * GB, nm0);
checkApplicationResourceUsage(1 * GB, application0);
checkApplicationResourceUsage(3 * GB, application1);
nodeUpdate(rm, nm0);
application0.schedule();
checkApplicationResourceUsage(3 * GB, application0);
application1.schedule();
checkApplicationResourceUsage(7 * GB, application1);
checkNodeResourceUsage(10 * GB, nm0);


log.info("start testassignmultiple");
}

};