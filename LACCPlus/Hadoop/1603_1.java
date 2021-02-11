//,temp,TestCopyMapper.java,643,727,temp,TestCopyMapper.java,425,497
//,3
public class xxx {
  @Test(timeout=40000)
  public void testFailCopyWithAccessControlException() {
    try {
      deleteState();
      createSourceData();

      UserGroupInformation tmpUser = UserGroupInformation.createRemoteUser("guest");

      final CopyMapper copyMapper = new CopyMapper();

      final StubContext stubContext =  tmpUser.
          doAs(new PrivilegedAction<StubContext>() {
        @Override
        public StubContext run() {
          try {
            return new StubContext(getConfiguration(), null, 0);
          } catch (Exception e) {
            LOG.error("Exception encountered ", e);
            throw new RuntimeException(e);
          }
        }
      });

      EnumSet<DistCpOptions.FileAttribute> preserveStatus =
          EnumSet.allOf(DistCpOptions.FileAttribute.class);
      preserveStatus.remove(DistCpOptions.FileAttribute.ACL);
      preserveStatus.remove(DistCpOptions.FileAttribute.XATTR);

      final Mapper<Text, CopyListingFileStatus, Text, Text>.Context context
              = stubContext.getContext();

      context.getConfiguration().set(DistCpConstants.CONF_LABEL_PRESERVE_STATUS,
        DistCpUtils.packAttributes(preserveStatus));

      touchFile(SOURCE_PATH + "/src/file");
      OutputStream out = cluster.getFileSystem().create(new Path(TARGET_PATH + "/src/file"));
      out.write("hello world".getBytes());
      out.close();
      cluster.getFileSystem().setPermission(new Path(SOURCE_PATH + "/src/file"),
          new FsPermission(FsAction.READ, FsAction.READ, FsAction.READ));
      cluster.getFileSystem().setPermission(new Path(TARGET_PATH + "/src/file"),
          new FsPermission(FsAction.READ, FsAction.READ, FsAction.READ));

      final FileSystem tmpFS = tmpUser.doAs(new PrivilegedAction<FileSystem>() {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(configuration);
          } catch (IOException e) {
            LOG.error("Exception encountered ", e);
            Assert.fail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test ought to fail here");
          }
        }
      });

      tmpUser.doAs(new PrivilegedAction<Integer>() {
        @Override
        public Integer run() {
          try {
            copyMapper.setup(context);
            copyMapper.map(new Text("/src/file"),
                new CopyListingFileStatus(tmpFS.getFileStatus(
                  new Path(SOURCE_PATH + "/src/file"))),
                context);
            Assert.fail("Didn't expect the file to be copied");
          } catch (AccessControlException ignore) {
          } catch (Exception e) {
            // We want to make sure the underlying cause of the exception is
            // due to permissions error. The exception we're interested in is
            // wrapped twice - once in RetriableCommand and again in CopyMapper
            // itself.
            if (e.getCause() == null || e.getCause().getCause() == null ||
                !(e.getCause().getCause() instanceof AccessControlException)) {
              throw new RuntimeException(e);
            }
          }
          return null;
        }
      });
    } catch (Exception e) {
      LOG.error("Exception encountered ", e);
      Assert.fail("Test failed: " + e.getMessage());
    }
  }

};