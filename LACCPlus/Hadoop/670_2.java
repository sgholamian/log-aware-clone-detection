//,temp,AbstractContractRootDirectoryTest.java,91,107,temp,AbstractContractRootDirectoryTest.java,58,67
//,3
public class xxx {
  @Test
  public void testRmEmptyRootDirNonRecursive() throws Throwable {
    //extra sanity checks here to avoid support calls about complete loss of data
    skipIfUnsupported(TEST_ROOT_TESTS_ENABLED);
    Path root = new Path("/");
    ContractTestUtils.assertIsDirectory(getFileSystem(), root);
    boolean deleted = getFileSystem().delete(root, true);
    LOG.info("rm / of empty dir result is {}", deleted);
    ContractTestUtils.assertIsDirectory(getFileSystem(), root);
  }

};