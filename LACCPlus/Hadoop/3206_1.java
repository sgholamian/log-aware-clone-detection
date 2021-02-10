//,temp,TestCopyMapper.java,895,971,temp,TestCopyMapper.java,513,585
//,3
public class xxx {
  private void doTestIgnoreFailuresDoubleWrapped(final boolean ignoreFailures) {
    try {
      deleteState();
      createSourceData();

      final UserGroupInformation tmpUser = UserGroupInformation
          .createRemoteUser("guest");

      final CopyMapper copyMapper = new CopyMapper();

      final Mapper<Text, CopyListingFileStatus, Text, Text>.Context context =
          tmpUser.doAs(new PrivilegedAction<
              Mapper<Text, CopyListingFileStatus, Text, Text>.Context>() {
            @Override
            public Mapper<Text, CopyListingFileStatus, Text, Text>.Context
            run() {
              try {
                StubContext stubContext = new StubContext(
                    getConfiguration(), null, 0);
                return stubContext.getContext();
              } catch (Exception e) {
                LOG.error("Exception encountered when get stub context", e);
                throw new RuntimeException(e);
              }
            }
          });

      touchFile(SOURCE_PATH + "/src/file");
      mkdirs(TARGET_PATH);
      cluster.getFileSystem().setPermission(new Path(SOURCE_PATH + "/src/file"),
          new FsPermission(FsAction.NONE, FsAction.NONE, FsAction.NONE));
      cluster.getFileSystem().setPermission(new Path(TARGET_PATH),
          new FsPermission((short)511));

      context.getConfiguration().setBoolean(
          DistCpOptionSwitch.IGNORE_FAILURES.getConfigLabel(), ignoreFailures);

      final FileSystem tmpFS = tmpUser.doAs(new PrivilegedAction<FileSystem>() {
        @Override
        public FileSystem run() {
          try {
            return FileSystem.get(cluster.getConfiguration(0));
          } catch (IOException e) {
            LOG.error("Exception encountered when get FileSystem.", e);
            throw new RuntimeException(e);
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
            Assert.assertTrue("Should have thrown an IOException if not " +
                "ignoring failures", ignoreFailures);
          } catch (IOException e) {
            LOG.error("Unexpected exception encountered. ", e);
            Assert.assertFalse("Should not have thrown an IOException if " +
                "ignoring failures", ignoreFailures);
            // the IOException is not thrown again as it's expected
          } catch (Exception e) {
            LOG.error("Exception encountered when the mapper copies file.", e);
            throw new RuntimeException(e);
          }
          return null;
        }
      });
    } catch (Exception e) {
      LOG.error("Unexpected exception encountered. ", e);
      Assert.fail("Test failed: " + e.getMessage());
    }
  }

};