//,temp,TestLazyPersistFiles.java,70,84,temp,TestLazyPersistFiles.java,49,64
//,3
public class xxx {
  @Test
  public void testAppendIsDenied() throws IOException {
    getClusterBuilder().build();
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    Path path = new Path("/" + METHOD_NAME + ".dat");

    makeTestFile(path, BLOCK_SIZE, true);

    try {
      client.append(path.toString(), BUFFER_LENGTH,
          EnumSet.of(CreateFlag.APPEND), null, null).close();
      fail("Append to LazyPersist file did not fail as expected");
    } catch (Throwable t) {
      LOG.info("Got expected exception ", t);
    }
  }

};