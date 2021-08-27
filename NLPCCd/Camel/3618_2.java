//,temp,sample_5433.java,2,16,temp,sample_1437.java,2,16
//,3
public class xxx {
public void dummy_method(){
statusHandler.reset();
data.put("Name", "MyCarManufacturer Patched");
olingoApp.patch(edm, TEST_CREATE_MANUFACTURER, null, data, statusHandler);
statusHandler.await();
entryHandler.reset();
olingoApp.read(edm, TEST_CREATE_MANUFACTURER, null, null, entryHandler);
ODataEntry updatedEntry = entryHandler.await();
statusHandler.reset();
olingoApp.delete(TEST_CREATE_MANUFACTURER, null, statusHandler);
HttpStatusCodes statusCode = statusHandler.await();


log.info("deletion of entry was successful");
}

};