//,temp,sample_5509.java,2,11,temp,sample_5464.java,2,10
//,3
public class xxx {
public void testOnlyReplayingFlushStartDoesNotHoldUpRegionClose() throws IOException {
int start = 0;
putData(primaryRegion, Durability.SYNC_WAL, start, 100, cq, families);
primaryRegion.flush(true);
reader = createWALReaderForPrimary();


log.info("replaying edits and flush events in secondary");
}

};