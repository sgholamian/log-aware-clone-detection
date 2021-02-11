//,temp,TestJournalNodeRespectsBindHostKeys.java,169,199,temp,TestJournalNodeRespectsBindHostKeys.java,115,141
//,3
public class xxx {
  @Test (timeout=300000)
  public void testHttpsBindHostKey() throws Exception {
    LOG.info("Testing behavior without " + DFS_JOURNALNODE_HTTPS_BIND_HOST_KEY);

    setupSsl();

    conf.set(DFS_HTTP_POLICY_KEY, HttpConfig.Policy.HTTPS_ONLY.name());

    // NN should not bind the wildcard address by default.
    conf.set(DFS_JOURNALNODE_HTTPS_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
    jCluster = new MiniJournalCluster.Builder(conf).format(true)
        .numJournalNodes(NUM_JN).build();
    jn = jCluster.getJournalNode(0);
    String address = jn.getHttpsAddress().toString();
    assertFalse("HTTP Bind address not expected to be wildcard by default.",
        address.startsWith(WILDCARD_ADDRESS));

    LOG.info("Testing behavior with " + DFS_JOURNALNODE_HTTPS_BIND_HOST_KEY);

    // Tell NN to bind the wildcard address.
    conf.set(DFS_JOURNALNODE_HTTPS_BIND_HOST_KEY, WILDCARD_ADDRESS);

    // Verify that NN binds wildcard address now.
    conf.set(DFS_JOURNALNODE_HTTPS_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
    jCluster = new MiniJournalCluster.Builder(conf).format(true)
        .numJournalNodes(NUM_JN).build();
    jn = jCluster.getJournalNode(0);
    address = jn.getHttpsAddress().toString();
    assertTrue("HTTP Bind address " + address + " is not wildcard.",
        address.startsWith(WILDCARD_ADDRESS));
  }

};