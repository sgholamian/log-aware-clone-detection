//,temp,AbstractMultiKahaDBDeletionTest.java,178,195,temp,AbstractMultiKahaDBDeletionTest.java,139,156
//,3
public class xxx {
    @Test
    public void testStoreCleanupDeleteDest2First() throws Exception {
        LOG.info("Creating {} first, {} second", dest1, dest2);
        LOG.info("Deleting {} first, {} second", dest2, dest1);

        // Create two destinations
        broker.addDestination(brokerService.getAdminConnectionContext(), dest1, false);
        broker.addDestination(brokerService.getAdminConnectionContext(), dest2, false);

        // remove both destinations
        broker.removeDestination(brokerService.getAdminConnectionContext(), dest2, 100);
        broker.removeDestination(brokerService.getAdminConnectionContext(), dest1, 100);

        //Assert that with no more destinations attached to a store that it has been cleaned up
        Collection<File> storeFiles = FileUtils.listFiles(storeDir, new WildcardFileFilter("db*"), getStoreFileFilter());
        assertEquals("Store files should be deleted", 0, storeFiles.size());

    }

};