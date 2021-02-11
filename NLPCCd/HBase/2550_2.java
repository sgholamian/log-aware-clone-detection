//,temp,sample_4889.java,2,16,temp,sample_4892.java,2,16
//,3
public class xxx {
public void dummy_method(){
zkconn.setData(node, dataOne, -1);
zkListener.waitForDataChange();
assertNotNull(localTracker.getData(false));
assertNotNull(localTracker.blockUntilAvailable());
assertTrue(Bytes.equals(localTracker.getData(false), dataOne));
assertNotNull(secondTracker.getData(false));
assertNotNull(secondTracker.blockUntilAvailable());
assertTrue(Bytes.equals(secondTracker.getData(false), dataOne));
assertTrue(thread.hasData);
assertTrue(Bytes.equals(thread.tracker.getData(false), dataOne));


log.info("successfully got data one following a data change on all trackers and threads");
}

};