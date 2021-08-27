//,temp,KahaDBIndexLocationTest.java,108,141,temp,MKahaDBIndexLocationTest.java,94,138
//,3
public class xxx {
    @Test
    public void testIndexDirExists() throws Exception {

        produceMessages();

        LOG.info("Index dir is configured as: {}", kahaIndexDir);
        assertTrue(kahaDataDir.exists());
        assertTrue(kahaIndexDir.exists());


        String destName = nameFromDestinationFilter(queue);
        String[] index = new File(kahaIndexDir, destName).list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing index filename: {}", name);
                return name.endsWith("data") || name.endsWith("redo");
            }
        });

        String[] journal = new File(kahaDataDir, destName).list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing log filename: {}", name);
                return name.endsWith("log") || name.equals("lock");
            }
        });


        // Should be db.data and db.redo and nothing else.
        assertNotNull(index);
        assertEquals(2, index.length);

        // Should contain the initial log for the journal
        assertNotNull(journal);
        assertEquals(1, journal.length);

        stopBroker();
        createBroker();
        broker.start();
        broker.waitUntilStarted();

        consume();
    }

};