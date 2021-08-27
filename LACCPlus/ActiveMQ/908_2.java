//,temp,AbstractMultiKahaDBDeletionTest.java,159,176,temp,AbstractMultiKahaDBDeletionTest.java,119,136
//,3
public class xxx {
    @Test
    public void testDest1Deletion() throws Exception {
        LOG.info("Creating {} first, {} second", dest1, dest2);
        LOG.info("Removing {}, subscribing to {}", dest1, dest2);

        // Create two destinations
        broker.addDestination(brokerService.getAdminConnectionContext(), dest1, false);
        broker.addDestination(brokerService.getAdminConnectionContext(), dest2, false);

        // remove destination2
        broker.removeDestination(brokerService.getAdminConnectionContext(), dest1, 100);

        // try and create a consumer on dest2, before AMQ-5875 this
        //would cause an IllegalStateException for Topics
        createConsumer(dest2);
        Collection<File> storeFiles = FileUtils.listFiles(storeDir, new WildcardFileFilter("db*"), getStoreFileFilter());
        assertTrue("Store index should still exist", storeFiles.size() >= 1);
    }

};