//,temp,TestSaveNamespace.java,364,426,temp,TestSaveNamespace.java,122,220
//,3
public class xxx {
  private void saveNamespaceWithInjectedFault(Fault fault) throws Exception {
    Configuration conf = getConf();
    NameNode.initMetrics(conf, NamenodeRole.NAMENODE);
    DFSTestUtil.formatNameNode(conf);
    FSNamesystem fsn = FSNamesystem.loadFromDisk(conf);

    // Replace the FSImage with a spy
    FSImage originalImage = fsn.getFSImage();
    NNStorage storage = originalImage.getStorage();

    NNStorage spyStorage = spy(storage);
    originalImage.storage = spyStorage;

    FSImage spyImage = spy(originalImage);
    Whitebox.setInternalState(fsn, "fsImage", spyImage);

    boolean shouldFail = false; // should we expect the save operation to fail
    // inject fault
    switch(fault) {
    case SAVE_SECOND_FSIMAGE_RTE:
      // The spy throws a RuntimeException when writing to the second directory
      doAnswer(new FaultySaveImage(true)).
        when(spyImage).saveFSImage(
            (SaveNamespaceContext)anyObject(),
            (StorageDirectory)anyObject(), (NameNodeFile) anyObject());
      shouldFail = false;
      break;
    case SAVE_SECOND_FSIMAGE_IOE:
      // The spy throws an IOException when writing to the second directory
      doAnswer(new FaultySaveImage(false)).
        when(spyImage).saveFSImage(
            (SaveNamespaceContext)anyObject(),
            (StorageDirectory)anyObject(), (NameNodeFile) anyObject());
      shouldFail = false;
      break;
    case SAVE_ALL_FSIMAGES:
      // The spy throws IOException in all directories
      doThrow(new RuntimeException("Injected")).
      when(spyImage).saveFSImage(
          (SaveNamespaceContext)anyObject(),
          (StorageDirectory)anyObject(), (NameNodeFile) anyObject());
      shouldFail = true;
      break;
    case WRITE_STORAGE_ALL:
      // The spy throws an exception before writing any VERSION files
      doThrow(new RuntimeException("Injected"))
        .when(spyStorage).writeAll();
      shouldFail = true;
      break;
    case WRITE_STORAGE_ONE:
      // The spy throws on exception on one particular storage directory
      doAnswer(new FaultySaveImage(true))
        .when(spyStorage).writeProperties((StorageDirectory)anyObject());
      // TODO: unfortunately this fails -- should be improved.
      // See HDFS-2173.
      shouldFail = true;
      break;
    }

    try {
      doAnEdit(fsn, 1);

      // Save namespace - this may fail, depending on fault injected
      fsn.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
      try {
        fsn.saveNamespace();
        if (shouldFail) {
          fail("Did not fail!");
        }
      } catch (Exception e) {
        if (! shouldFail) {
          throw e;
        } else {
          LOG.info("Test caught expected exception", e);
        }
      }
      
      fsn.setSafeMode(SafeModeAction.SAFEMODE_LEAVE);
      // Should still be able to perform edits
      doAnEdit(fsn, 2);

      // Now shut down and restart the namesystem
      originalImage.close();
      fsn.close();      
      fsn = null;

      // Start a new namesystem, which should be able to recover
      // the namespace from the previous incarnation.
      fsn = FSNamesystem.loadFromDisk(conf);

      // Make sure the image loaded including our edits.
      checkEditExists(fsn, 1);
      checkEditExists(fsn, 2);
    } finally {
      if (fsn != null) {
        fsn.close();
      }
    }
  }

};