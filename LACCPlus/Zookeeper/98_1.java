//,temp,QuorumBase.java,361,381,temp,ClientBase.java,613,647
//,3
public class xxx {
    @Override
    public void tearDown() throws Exception {
        LOG.info("TearDown started");

        OSMXBean osMbean = new OSMXBean();
        if (osMbean.getUnix() == true) {
            LOG.info("fdcount after test is: "
                    + osMbean.getOpenFileDescriptorCount());
        }

        shutdownServers();

        for (String hp : hostPort.split(",")) {
            Assert.assertTrue("waiting for server down",
                       ClientBase.waitForServerDown(hp,
                                           ClientBase.CONNECTION_TIMEOUT));
            LOG.info(hp + " is no longer accepting client connections");
        }

        JMXEnv.tearDown();
    }

};