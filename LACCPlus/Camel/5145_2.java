//,temp,Olingo2ComponentProducerTest.java,171,200,temp,Olingo2ComponentProducerTest.java,134,169
//,3
public class xxx {
    @Test
    public void testCreateUpdateDelete() throws Exception {
        final Map<String, Object> data = getEntityData();
        Map<String, Object> address;

        final ODataEntry manufacturer = requestBody("direct:CREATE", data);
        assertNotNull(manufacturer, "Created Manufacturer");
        final Map<String, Object> properties = manufacturer.getProperties();
        assertEquals(TEST_CREATE_MANUFACTURER_ID, properties.get(ID_PROPERTY), "Created Manufacturer Id");
        LOG.info("Created Manufacturer: {}", properties);

        // update
        data.put("Name", "MyCarManufacturer Renamed");
        address = (Map<String, Object>) data.get("Address");
        address.put("Street", "Main Street");

        HttpStatusCodes status = requestBody("direct:UPDATE", data);
        assertNotNull(status, "Update status");
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), status.getStatusCode(), "Update status");
        LOG.info("Update status: {}", status);

        final Map<String, Object> headers = new HashMap<>();
        headers.put(Olingo2Constants.PROPERTY_PREFIX + "keyPredicate", String.format("'%s'", TEST_CREATE_MANUFACTURER_ID));
        final ODataEntry updatedManufacturer = requestBodyAndHeaders("direct:READENTRY", null, headers);
        assertNotNull(updatedManufacturer);
        final Map<String, Object> updatedProperties = updatedManufacturer.getProperties();
        assertEquals(TEST_CREATE_MANUFACTURER_ID, updatedProperties.get(ID_PROPERTY), "Manufacturer Id");
        assertEquals("MyCarManufacturer Renamed", updatedProperties.get("Name"), "Manufacturer Name");
        LOG.info("Updated Manufacturer: {}", updatedProperties);

        // delete
        status = requestBody("direct:DELETE", null);
        assertNotNull(status, "Delete status");
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), status.getStatusCode(), "Delete status");
        LOG.info("Delete status: {}", status);
    }

};