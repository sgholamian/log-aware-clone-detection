//,temp,sample_5421.java,2,14,temp,sample_5425.java,2,16
//,3
public class xxx {
public void dummy_method(){
olingoApp.uread(edm, TEST_PEOPLE, null, null, responseHandler);
rawEntity = responseHandler.await();
entity = reader.readEntity(rawEntity, TEST_FORMAT);
assertEquals("Russell", entity.getProperty("FirstName").getValue().toString());
responseHandler.reset();
final Map<String, String> queryParams = new HashMap<String, String>();
queryParams.put(SystemQueryOptionKind.EXPAND.toString(), TRIPS);
olingoApp.uread(edm, TEST_PEOPLE, queryParams, null, responseHandler);
rawEntity = responseHandler.await();
entity = reader.readEntity(rawEntity, TEST_FORMAT);


log.info("single people entiry with expanded trips relation");
}

};