//,temp,Olingo2AppAPITest.java,127,136,temp,Olingo4AppAPITest.java,196,206
//,3
public class xxx {
    @Test
    public void testReadEntitySet() throws Exception {
        final TestOlingo4ResponseHandler<ClientEntitySet> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(edm, PEOPLE, null, null, responseHandler);

        final ClientEntitySet entitySet = responseHandler.await();
        assertNotNull(entitySet);
        assertEquals(20, entitySet.getEntities().size(), "Entity set count");
        LOG.info("Entities:  {}", prettyPrint(entitySet));
    }

};