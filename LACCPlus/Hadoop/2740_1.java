//,temp,TestLazyPersistFiles.java,70,84,temp,TestLazyPersistFiles.java,49,64
//,3
public class xxx {
  @Test
  public void testTruncateIsDenied() throws IOException {
    getClusterBuilder().build();
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path = new Path("/" + METHOD_NAME + ".dat");

    makeTestFile(path, BLOCK_SIZE, true);

    try {
      client.truncate(path.toString(), BLOCK_SIZE/2);
      fail("Truncate to LazyPersist file did not fail as expected");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
    }
  }

};