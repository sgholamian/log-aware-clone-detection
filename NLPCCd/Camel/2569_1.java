//,temp,sample_1439.java,2,17,temp,sample_5435.java,2,17
//,2
public class xxx {
public void dummy_method(){
ODataEntry updatedEntry = entryHandler.await();
statusHandler.reset();
olingoApp.delete(TEST_CREATE_MANUFACTURER, null, statusHandler);
HttpStatusCodes statusCode = statusHandler.await();
try {
entryHandler.reset();
olingoApp.read(edm, TEST_CREATE_MANUFACTURER, null, null, entryHandler);
entryHandler.await();
fail("Entry not deleted!");
} catch (Exception e) {


log.info("deleted entry not found");
}
}

};