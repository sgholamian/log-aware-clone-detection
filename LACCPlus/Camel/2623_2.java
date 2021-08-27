//,temp,Olingo2AppAPITest.java,339,389,temp,Olingo2AppAPITest.java,206,261
//,3
public class xxx {
    @Test
    public void testReadUpdateProperties() throws Exception {
        // test simple property Manufacturer.Founded
        final TestOlingo2ResponseHandler<Map<String, Object>> propertyHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, null, null, propertyHandler);

        Calendar founded = (Calendar) propertyHandler.await().get(FOUNDED_PROPERTY);
        LOG.info("Founded property {}", founded);

        final TestOlingo2ResponseHandler<Calendar> valueHandler = new TestOlingo2ResponseHandler<>();

        olingoApp.read(edm, TEST_MANUFACTURER_FOUNDED_VALUE, null, null, valueHandler);

        founded = valueHandler.await();
        LOG.info("Founded property {}", founded);

        final TestOlingo2ResponseHandler<HttpStatusCodes> statusHandler = new TestOlingo2ResponseHandler<>();
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put(FOUNDED_PROPERTY, new Date());

        //        olingoApp.update(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, properties, statusHandler);
        // requires a plain Date for XML
        olingoApp.update(edm, TEST_MANUFACTURER_FOUNDED_PROPERTY, null, new Date(), statusHandler);

        LOG.info("Founded property updated with status {}", statusHandler.await().getStatusCode());

        statusHandler.reset();

        olingoApp.update(edm, TEST_MANUFACTURER_FOUNDED_VALUE, null, new Date(), statusHandler);

        LOG.info("Founded property updated with status {}", statusHandler.await().getStatusCode());

        // test complex property Manufacturer.Address
        propertyHandler.reset();

        olingoApp.read(edm, TEST_MANUFACTURER_ADDRESS_PROPERTY, null, null, propertyHandler);

        final Map<String, Object> address = propertyHandler.await();
        LOG.info("Address property {}", prettyPrint(address, 0));

        statusHandler.reset();

        address.clear();
        // Olingo2 sample server MERGE/PATCH behaves like PUT!!!
        //        address.put("Street", "Main Street");
        address.put("Street", "Star Street 137");
        address.put("City", "Stuttgart");
        address.put("ZipCode", "70173");
        address.put("Country", "Germany");

        //        olingoApp.patch(edm, TEST_MANUFACTURER_ADDRESS_PROPERTY, address, statusHandler);
        olingoApp.merge(edm, TEST_MANUFACTURER_ADDRESS_PROPERTY, null, address, statusHandler);

        LOG.info("Address property updated with status {}", statusHandler.await().getStatusCode());
    }

};