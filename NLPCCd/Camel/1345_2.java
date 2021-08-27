//,temp,sample_1421.java,2,14,temp,sample_1422.java,2,16
//,3
public class xxx {
public void dummy_method(){
responseHandler.reset();
olingoApp.uread(edm, TEST_CAR, null, null, responseHandler);
rawentry = responseHandler.await();
entry = EntityProvider.readEntry(TEST_FORMAT_STRING, edmEntitySetMap.get(CARS), rawentry, EntityProviderReadProperties.init().build());
responseHandler.reset();
final Map<String, String> queryParams = new HashMap<String, String>();
queryParams.put(SystemQueryOption.$expand.toString(), CARS);
olingoApp.uread(edm, TEST_MANUFACTURER, queryParams, null, responseHandler);
rawentry = responseHandler.await();
ODataEntry entryExpanded = EntityProvider.readEntry(TEST_FORMAT_STRING, edmEntitySetMap.get(MANUFACTURERS), rawentry, EntityProviderReadProperties.init().build());


log.info("single entry with expanded cars relation");
}

};