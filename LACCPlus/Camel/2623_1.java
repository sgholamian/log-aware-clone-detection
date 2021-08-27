//,temp,Olingo2AppAPITest.java,339,389,temp,Olingo2AppAPITest.java,206,261
//,3
public class xxx {
    @Test
    public void testCreateUpdateDeleteEntry() throws Exception {

        // create entry to update
        final TestOlingo2ResponseHandler<ODataEntry> entryHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.create(edm, MANUFACTURERS, null, getEntityData(), entryHandler);

        ODataEntry createdEntry = entryHandler.await();
        LOG.info("Created Entry:  {}", prettyPrint(createdEntry));

        Map<String, Object> data = getEntityData();
        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) data.get(ADDRESS);

        data.put("Name", "MyCarManufacturer Renamed");
        address.put("Street", "Main Street");
        final TestOlingo2ResponseHandler<HttpStatusCodes> statusHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.update(edm, TEST_CREATE_MANUFACTURER, null, data, statusHandler);
        statusHandler.await();

        statusHandler.reset();
        data.put("Name", "MyCarManufacturer Patched");
        olingoApp.patch(edm, TEST_CREATE_MANUFACTURER, null, data, statusHandler);
        statusHandler.await();

        entryHandler.reset();
        olingoApp.read(edm, TEST_CREATE_MANUFACTURER, null, null, entryHandler);

        ODataEntry updatedEntry = entryHandler.await();
        LOG.info("Updated Entry successfully:  {}", prettyPrint(updatedEntry));

        statusHandler.reset();
        olingoApp.delete(TEST_CREATE_MANUFACTURER, null, statusHandler);

        HttpStatusCodes statusCode = statusHandler.await();
        LOG.info("Deletion of Entry was successful:  {}: {}", statusCode.getStatusCode(), statusCode.getInfo());

        try {
            LOG.info("Verify Delete Entry");

            entryHandler.reset();
            olingoApp.read(edm, TEST_CREATE_MANUFACTURER, null, null, entryHandler);

            entryHandler.await();
            fail("Entry not deleted!");
        } catch (Exception e) {
            LOG.info("Deleted entry not found: {}", e.getMessage());
        }
    }

};