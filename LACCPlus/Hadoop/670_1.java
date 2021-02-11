//,temp,AbstractContractRootDirectoryTest.java,91,107,temp,AbstractContractRootDirectoryTest.java,58,67
//,3
public class xxx {
  @Test
  public void testRmRootRecursive() throws Throwable {
    //extra sanity checks here to avoid support calls about complete loss of data
    skipIfUnsupported(TEST_ROOT_TESTS_ENABLED);
    Path root = new Path("/");
    ContractTestUtils.assertIsDirectory(getFileSystem(), root);
    Path file = new Path("/testRmRootRecursive");
    ContractTestUtils.touch(getFileSystem(), file);
    boolean deleted = getFileSystem().delete(root, true);
    ContractTestUtils.assertIsDirectory(getFileSystem(), root);
    LOG.info("rm -rf / result is {}", deleted);
    if (deleted) {
      assertPathDoesNotExist("expected file to be deleted", file);
    } else {
      assertPathExists("expected file to be preserved", file);;
    }
  }

};