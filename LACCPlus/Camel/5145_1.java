//,temp,Olingo2ComponentProducerTest.java,171,200,temp,Olingo2ComponentProducerTest.java,134,169
//,3
public class xxx {
    @Test
    public void testCreateMerge() throws Exception {
        final Map<String, Object> data = getEntityData();
        data.put(ID_PROPERTY, TEST_MERGE_MANUFACTURER_ID);

        final ODataEntry manufacturer = requestBody("direct:CREATE", data);
        assertNotNull(manufacturer, "Created Manufacturer");
        final Map<String, Object> properties = manufacturer.getProperties();
        assertEquals(TEST_MERGE_MANUFACTURER_ID, properties.get(ID_PROPERTY), "Created Manufacturer Id");
        LOG.info("Created Manufacturer: {}", properties);

        final Map<String, Object> propertiesToUpdate = new HashMap<>();
        propertiesToUpdate.put(ID_PROPERTY, TEST_MERGE_MANUFACTURER_ID);
        propertiesToUpdate.put("Name", "MyCarManufacturer Updated");

        HttpStatusCodes status = requestBody("direct:MERGE", propertiesToUpdate);
        assertNotNull(status, "Merge status");
        assertEquals(HttpStatusCodes.NO_CONTENT.getStatusCode(), status.getStatusCode(), "Merge status");
        LOG.info("Merge status: {}", status);

        final Map<String, Object> headers = new HashMap<>();
        headers.put(Olingo2Constants.PROPERTY_PREFIX + "keyPredicate", String.format("'%s'", TEST_MERGE_MANUFACTURER_ID));
        final ODataEntry mergedManufacturer = requestBodyAndHeaders("direct:READENTRY", null, headers);
        assertNotNull(mergedManufacturer);
        final Map<String, Object> mergedProperties = mergedManufacturer.getProperties();
        assertEquals(TEST_MERGE_MANUFACTURER_ID, mergedProperties.get(ID_PROPERTY), "Manufacturer Id");
        assertEquals("MyCarManufacturer Updated", mergedProperties.get("Name"), "Manufacturer Name");
        assertNotNull(mergedProperties.get("Address"), "Manufacturer Address");
        LOG.info("Merged Manufacturer: {}", mergedProperties);
    }

};