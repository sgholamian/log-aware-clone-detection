//,temp,TestMRJobs.java,743,805,temp,TestMRWithDistributedCache.java,90,137
//,3
public class xxx {
    @Override
    public void setup(Context context) throws IOException {
      Configuration conf = context.getConfiguration();
      Path[] localFiles = context.getLocalCacheFiles();
      URI[] files = context.getCacheFiles();
      Path[] localArchives = context.getLocalCacheArchives();
      URI[] archives = context.getCacheArchives();

      // Check that 4 (2 + appjar + DistrubutedCacheChecker jar) files 
      // and 2 archives are present
      Assert.assertEquals(4, localFiles.length);
      Assert.assertEquals(4, files.length);
      Assert.assertEquals(2, localArchives.length);
      Assert.assertEquals(2, archives.length);

      // Check lengths of the files
      Map<String, Path> filesMap = pathsToMap(localFiles);
      Assert.assertTrue(filesMap.containsKey("distributed.first.symlink"));
      Assert.assertEquals(1, localFs.getFileStatus(
        filesMap.get("distributed.first.symlink")).getLen());
      Assert.assertTrue(filesMap.containsKey("distributed.second.jar"));
      Assert.assertTrue(localFs.getFileStatus(
        filesMap.get("distributed.second.jar")).getLen() > 1);

      // Check extraction of the archive
      Map<String, Path> archivesMap = pathsToMap(localArchives);
      Assert.assertTrue(archivesMap.containsKey("distributed.third.jar"));
      Assert.assertTrue(localFs.exists(new Path(
        archivesMap.get("distributed.third.jar"), "distributed.jar.inside3")));
      Assert.assertTrue(archivesMap.containsKey("distributed.fourth.jar"));
      Assert.assertTrue(localFs.exists(new Path(
        archivesMap.get("distributed.fourth.jar"), "distributed.jar.inside4")));

      // Check the class loaders
      LOG.info("Java Classpath: " + System.getProperty("java.class.path"));
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      // Both the file and the archive should have been added to classpath, so
      // both should be reachable via the class loader.
      Assert.assertNotNull(cl.getResource("distributed.jar.inside2"));
      Assert.assertNotNull(cl.getResource("distributed.jar.inside3"));
      Assert.assertNotNull(cl.getResource("distributed.jar.inside4"));
      // The Job Jar should have been extracted to a folder named "job.jar" and
      // added to the classpath; the two jar files in the lib folder in the Job
      // Jar should have also been added to the classpath
      Assert.assertNotNull(cl.getResource("job.jar/"));
      Assert.assertNotNull(cl.getResource("job.jar/lib/lib1.jar"));
      Assert.assertNotNull(cl.getResource("job.jar/lib/lib2.jar"));

      // Check that the symlink for the renaming was created in the cwd;
      File symlinkFile = new File("distributed.first.symlink");
      Assert.assertTrue(symlinkFile.exists());
      Assert.assertEquals(1, symlinkFile.length());
      
      // Check that the symlink for the Job Jar was created in the cwd and
      // points to the extracted directory
      File jobJarDir = new File("job.jar");
      if (Shell.WINDOWS) {
        Assert.assertTrue(isWindowsSymlinkedDirectory(jobJarDir));
      } else {
        Assert.assertTrue(FileUtils.isSymlink(jobJarDir));
        Assert.assertTrue(jobJarDir.isDirectory());
      }
    }

};