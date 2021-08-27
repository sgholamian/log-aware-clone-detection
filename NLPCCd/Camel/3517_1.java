//,temp,sample_1436.java,2,16,temp,sample_1426.java,2,16
//,3
public class xxx {
public void dummy_method(){
final TestOlingo2ResponseHandler<HttpStatusCodes> statusHandler = new TestOlingo2ResponseHandler<HttpStatusCodes>();
olingoApp.update(edm, TEST_CREATE_MANUFACTURER, null, data, statusHandler);
statusHandler.await();
statusHandler.reset();
data.put("Name", "MyCarManufacturer Patched");
olingoApp.patch(edm, TEST_CREATE_MANUFACTURER, null, data, statusHandler);
statusHandler.await();
entryHandler.reset();
olingoApp.read(edm, TEST_CREATE_MANUFACTURER, null, null, entryHandler);
ODataEntry updatedEntry = entryHandler.await();


log.info("updated entry successfully");
}

};