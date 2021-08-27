//,temp,sample_5431.java,2,9,temp,sample_1435.java,2,9
//,2
public class xxx {
public void testCreateUpdateDeleteEntry() throws Exception {
final TestOlingo2ResponseHandler<ODataEntry> entryHandler = new TestOlingo2ResponseHandler<ODataEntry>();
olingoApp.create(edm, MANUFACTURERS, null, getEntityData(), entryHandler);
ODataEntry createdEntry = entryHandler.await();


log.info("created entry");
}

};