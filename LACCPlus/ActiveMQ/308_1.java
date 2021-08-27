//,temp,KahaDBIndexLocationTest.java,108,141,temp,MKahaDBIndexLocationTest.java,94,138
//,3
public class xxx {
    @Test
    public void testIndexDirExists() throws Exception {
        LOG.info("Index dir is configured as: {}", kahaIndexDir);
        assertTrue(kahaDataDir.exists());
        assertTrue(kahaIndexDir.exists());

        String[] index = kahaIndexDir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing filename: {}", name);
                return name.endsWith("data") || name.endsWith("redo");
            }
        });

        String[] journal = kahaDataDir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing filename: {}", name);
                return name.endsWith("log") || name.equals("lock");
            }
        });

        produceMessages();

        // Should be db.data and db.redo and nothing else.
        assertNotNull(index);
        assertEquals(2, index.length);

        // Should contain the initial log for the journal and the lock.
        assertNotNull(journal);
        assertEquals(2, journal.length);
    }

};