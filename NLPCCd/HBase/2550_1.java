//,temp,sample_4889.java,2,16,temp,sample_4892.java,2,16
//,3
public class xxx {
public void dummy_method(){
zkListener.waitForCreation();
thread.join();
assertNotNull(localTracker.getData(false));
assertNotNull(localTracker.blockUntilAvailable());
assertTrue(Bytes.equals(localTracker.getData(false), dataOne));
assertTrue(thread.hasData);
assertTrue(Bytes.equals(thread.tracker.getData(false), dataOne));
assertNotNull(secondTracker.getData(false));
assertNotNull(secondTracker.blockUntilAvailable());
assertTrue(Bytes.equals(secondTracker.getData(false), dataOne));


log.info("successfully got data one with the second tracker");
}

};