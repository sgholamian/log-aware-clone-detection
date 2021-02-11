//,temp,TestCopyMapper.java,643,727,temp,TestCopyMapper.java,564,641
//,3
public class xxx {
  @Test(timeout=40000)
  public void testSkipCopyNoPerms() {
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

      final Mapper<Text, CopyListingFileStatus, Text, Text>.Context context =
        stubContext.getContext();
      EnumSet<DistCpOptions.FileAttribute> preserveStatus =
          EnumSet.allOf(DistCpOptions.FileAttribute.class);
      preserveStatus.remove(DistCpOptions.FileAttribute.ACL);
      preserveStatus.remove(DistCpOptions.FileAttribute.XATTR);
      preserveStatus.remove(DistCpOptions.FileAttribute.TIMES);

      context.getConfiguration().set(DistCpConstants.CONF_LABEL_PRESERVE_STATUS,
        DistCpUtils.packAttributes(preserveStatus));

      touchFile(SOURCE_PATH + "/src/file");
      touchFile(TARGET_PATH + "/src/file");
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
            Assert.assertEquals(stubContext.getWriter().values().size(), 1);
            Assert.assertTrue(stubContext.getWriter().values().get(0).toString().startsWith("SKIP"));
            Assert.assertTrue(stubContext.getWriter().values().get(0).toString().
                contains(SOURCE_PATH + "/src/file"));
          } catch (Exception e) {
            throw new RuntimeException(e);
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