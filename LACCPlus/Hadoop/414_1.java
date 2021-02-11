//,temp,TestCopyMapper.java,499,562,temp,TestCopyMapper.java,396,423
//,3
public class xxx {
  @Test(timeout=40000)
  public void testCopyReadableFiles() {
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

      touchFile(SOURCE_PATH + "/src/file");
      mkdirs(TARGET_PATH);
      cluster.getFileSystem().setPermission(new Path(SOURCE_PATH + "/src/file"),
          new FsPermission(FsAction.READ, FsAction.READ, FsAction.READ));
      cluster.getFileSystem().setPermission(new Path(TARGET_PATH), new FsPermission((short)511));

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