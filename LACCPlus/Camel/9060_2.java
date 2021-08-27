//,temp,Olingo2AppAPITest.java,138,150,temp,Olingo4AppAPITest.java,208,219
//,3
public class xxx {
    @Test
    public void testReadUnparsedEntitySet() throws Exception {
        final TestOlingo4ResponseHandler<InputStream> responseHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.uread(edm, PEOPLE, null, null, responseHandler);

        final InputStream rawEntitySet = responseHandler.await();
        assertNotNull(rawEntitySet, "Data entity set");
        final ClientEntitySet entitySet = reader.readEntitySet(rawEntitySet, TEST_FORMAT);
        assertEquals(20, entitySet.getEntities().size(), "Entity set count");
        LOG.info("Entries:  {}", prettyPrint(entitySet));
    }

};