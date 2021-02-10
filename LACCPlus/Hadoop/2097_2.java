//,temp,TestMRJobs.java,743,805,temp,TestMRWithDistributedCache.java,90,137
//,3
public class xxx {
    public void setup(TaskInputOutputContext<?, ?, ?, ?> context)
        throws IOException {
      Configuration conf = context.getConfiguration();
      Path[] localFiles = context.getLocalCacheFiles();
      URI[] files = context.getCacheFiles();
      Path[] localArchives = context.getLocalCacheArchives();
      URI[] archives = context.getCacheArchives();
      FileSystem fs = LocalFileSystem.get(conf);

      // Check that 2 files and 2 archives are present
      TestCase.assertEquals(2, localFiles.length);
      TestCase.assertEquals(2, localArchives.length);
      TestCase.assertEquals(2, files.length);
      TestCase.assertEquals(2, archives.length);

      // Check the file name
      TestCase.assertTrue(files[0].getPath().endsWith("distributed.first"));
      TestCase.assertTrue(files[1].getPath().endsWith("distributed.second.jar"));
      
      // Check lengths of the files
      TestCase.assertEquals(1, fs.getFileStatus(localFiles[0]).getLen());
      TestCase.assertTrue(fs.getFileStatus(localFiles[1]).getLen() > 1);

      // Check extraction of the archive
      TestCase.assertTrue(fs.exists(new Path(localArchives[0],
          "distributed.jar.inside3")));
      TestCase.assertTrue(fs.exists(new Path(localArchives[1],
          "distributed.jar.inside4")));

      // Check the class loaders
      LOG.info("Java Classpath: " + System.getProperty("java.class.path"));
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      // Both the file and the archive were added to classpath, so both
      // should be reachable via the class loader.
      TestCase.assertNotNull(cl.getResource("distributed.jar.inside2"));
      TestCase.assertNotNull(cl.getResource("distributed.jar.inside3"));
      TestCase.assertNull(cl.getResource("distributed.jar.inside4"));

      // Check that the symlink for the renaming was created in the cwd;
      TestCase.assertTrue("symlink distributed.first.symlink doesn't exist",
          symlinkFile.exists());
      TestCase.assertEquals("symlink distributed.first.symlink length not 1", 1,
          symlinkFile.length());
      
      //This last one is a difference between MRv2 and MRv1
      TestCase.assertTrue("second file should be symlinked too",
          expectedAbsentSymlinkFile.exists());
    }

};