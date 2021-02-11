//,temp,TestJournalNodeRespectsBindHostKeys.java,169,199,temp,TestJournalNodeRespectsBindHostKeys.java,115,141
//,3
public class xxx {
  @Test(timeout=300000)
  public void testHttpBindHostKey() throws IOException {
    LOG.info("Testing without " + DFS_JOURNALNODE_HTTP_BIND_HOST_KEY);

    // NN should not bind the wildcard address by default.
    conf.set(DFS_JOURNALNODE_HTTP_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
    jCluster = new MiniJournalCluster.Builder(conf).format(true)
        .numJournalNodes(NUM_JN).build();
    jn = jCluster.getJournalNode(0);
    String address = jn.getHttpAddress().toString();
    assertFalse("HTTP Bind address not expected to be wildcard by default.",
        address.startsWith(WILDCARD_ADDRESS));

    LOG.info("Testing with " + DFS_JOURNALNODE_HTTP_BIND_HOST_KEY);

    // Tell NN to bind the wildcard address.
    conf.set(DFS_JOURNALNODE_HTTP_BIND_HOST_KEY, WILDCARD_ADDRESS);

    // Verify that NN binds wildcard address now.
    conf.set(DFS_JOURNALNODE_HTTP_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
    jCluster = new MiniJournalCluster.Builder(conf).format(true)
        .numJournalNodes(NUM_JN).build();
    jn = jCluster.getJournalNode(0);
    address = jn.getHttpAddress().toString();
    assertTrue("HTTP Bind address " + address + " is not wildcard.",
        address.startsWith(WILDCARD_ADDRESS));
  }

};