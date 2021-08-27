//,temp,Olingo4ComponentProducerTest.java,193,221,temp,Olingo4ComponentProducerTest.java,160,191
//,3
public class xxx {
    @Test
    public void testCreateUpdateDeleteFromJson() throws Exception {
        ClientEntity entity = requestBody("direct:create-entity", TEST_CREATE_JSON);
        assertNotNull(entity);
        assertEquals("Lewis", entity.getProperty("FirstName").getValue().toString());
        assertEquals("Black", entity.getProperty("LastName").getValue().toString());
        assertEquals("lewisblack", entity.getProperty("UserName").getValue().toString());
        assertEquals("", entity.getProperty("MiddleName").getValue().toString());

        // update
        HttpStatusCode status = requestBody("direct:update-entity", TEST_UPDATE_JSON);
        assertNotNull(status, "Update status");
        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), status.getStatusCode(), "Update status");
        LOG.info("Update entity status: {}", status);

        // delete
        status = requestBody("direct:delete-entity", null);
        assertNotNull(status, "Delete status");
        assertEquals(HttpStatusCode.NO_CONTENT.getStatusCode(), status.getStatusCode(), "Delete status");
        LOG.info("Delete status: {}", status);

        // check for delete
        try {
            requestBody("direct:read-deleted-entity", null);
        } catch (CamelExecutionException e) {
            String causeMsg = e.getCause().getMessage();
            assertTrue(causeMsg.contains("[HTTP/1.1 404 Not Found]"));
        }
    }

};