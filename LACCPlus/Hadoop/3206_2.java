//,temp,TestCopyMapper.java,895,971,temp,TestCopyMapper.java,513,585
//,3
public class xxx {
  @Test(timeout=40000)
  public void testPreserve() {
    try {
      deleteState();
      createSourceData();

      UserGroupInformation tmpUser = UserGroupInformation.createRemoteUser("guest");

      final CopyMapper copyMapper = new CopyMapper();

      final Mapper<Text, CopyListingFileStatus, Text, Text>.Context context =
        tmpUser.doAs(
          new PrivilegedAction<Mapper<Text, CopyListingFileStatus, Text, Text>.Context>() {
            @Override
            public Mapper<Text, CopyListingFileStatus, Text, Text>.Context run() {
              try {
                StubContext stubContext = new StubContext(getConfiguration(), null, 0);
                return stubContext.getContext();
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

      context.getConfiguration().set(DistCpConstants.CONF_LABEL_PRESERVE_STATUS,
        DistCpUtils.packAttributes(preserveStatus));

      touchFile(SOURCE_PATH + "/src/file");
      mkdirs(TARGET_PATH);
      cluster.getFileSystem().setPermission(new Path(TARGET_PATH), new FsPermission((short)511));

      final FileSystem tmpFS = tmpUser.doAs(new PrivilegedAction<FileSystem>() {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(cluster.getConfiguration(0));
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
            Assert.fail("Expected copy to fail");
          } catch (AccessControlException e) {
            Assert.assertTrue("Got exception: " + e.getMessage(), true);
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