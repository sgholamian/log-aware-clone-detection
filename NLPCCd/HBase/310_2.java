//,temp,sample_5488.java,2,9,temp,sample_5494.java,2,9
//,3
public class xxx {
public void testReplayFlushCommitMarkerWithoutFlushStartMarker(boolean droppableMemstore) throws IOException {
putDataWithFlushes(primaryRegion, 100, 100, droppableMemstore ? 0 : 100);
int numRows = droppableMemstore ? 100 : 200;
reader =  createWALReaderForPrimary();


log.info("replaying edits and flush events in secondary");
}

};