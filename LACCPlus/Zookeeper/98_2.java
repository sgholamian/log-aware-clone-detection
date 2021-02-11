//,temp,QuorumBase.java,361,381,temp,ClientBase.java,613,647
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        LOG.info("tearDown starting");

        tearDownAll();

        stopServer();

        if (tmpDir != null) {
            Assert.assertTrue("delete " + tmpDir.toString(), recursiveDelete(tmpDir));
        }

        // This has to be set to null when the same instance of this class is reused between test cases
        serverFactory = null;

        JMXEnv.tearDown();

        /* some useful information - log the number of fds used before
         * and after a test is run. Helps to verify we are freeing resources
         * correctly. Unfortunately this only works on unix systems (the
         * only place sun has implemented as part of the mgmt bean api.
         */
        OSMXBean osMbean = new OSMXBean();
        if (osMbean.getUnix() == true) {
            long fdCount = osMbean.getOpenFileDescriptorCount();
            String message = "fdcount after test is: "
                    + fdCount + " at start it was " + initialFdCount;
            LOG.info(message);
            if (fdCount > initialFdCount) {
                LOG.info("sleeping for 20 secs");
                //Thread.sleep(60000);
                //assertTrue(message, fdCount <= initialFdCount);
            }
        }
    }

};