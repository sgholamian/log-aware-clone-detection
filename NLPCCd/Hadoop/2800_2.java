//,temp,sample_4765.java,2,16,temp,sample_4766.java,2,16
//,2
public class xxx {
public void dummy_method(){
assigned = allocator.schedule();
rm.drainEvents();
assertBlacklistAdditionsAndRemovals(0, 0, rm);
Assert.assertEquals("No of assignments must be 0", 0, assigned.size());
assigned = allocator.schedule();
rm.drainEvents();
assertBlacklistAdditionsAndRemovals(0, 0, rm);
Assert.assertEquals("No of assignments must be 0", 0, assigned.size());
nodeManager3.nodeHeartbeat(true);
rm.drainEvents();


log.info("rm heartbeat to process the re scheduled containers for");
}

};