//,temp,TestSaveNamespace.java,364,426,temp,TestSaveNamespace.java,122,220
//,3
public class xxx {
  public void doTestFailedSaveNamespace(boolean restoreStorageAfterFailure)
  throws Exception {
    Configuration conf = getConf();
    NameNode.initMetrics(conf, NamenodeRole.NAMENODE);
    DFSTestUtil.formatNameNode(conf);
    FSNamesystem fsn = FSNamesystem.loadFromDisk(conf);

    // Replace the FSImage with a spy
    final FSImage originalImage = fsn.getFSImage();
    NNStorage storage = originalImage.getStorage();
    storage.close(); // unlock any directories that FSNamesystem's initialization may have locked

    NNStorage spyStorage = spy(storage);
    originalImage.storage = spyStorage;
    FSImage spyImage = spy(originalImage);
    Whitebox.setInternalState(fsn, "fsImage", spyImage);

    spyImage.storage.setStorageDirectories(
        FSNamesystem.getNamespaceDirs(conf), 
        FSNamesystem.getNamespaceEditsDirs(conf));

    doThrow(new IOException("Injected fault: saveFSImage")).
        when(spyImage).saveFSImage(
            (SaveNamespaceContext)anyObject(),
            (StorageDirectory)anyObject(), (NameNodeFile) anyObject());

    try {
      doAnEdit(fsn, 1);

      // Save namespace
      fsn.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
      try {
        fsn.saveNamespace();
        fail("saveNamespace did not fail even when all directories failed!");
      } catch (IOException ioe) {
        LOG.info("Got expected exception", ioe);
      }
      
      // Ensure that, if storage dirs come back online, things work again.
      if (restoreStorageAfterFailure) {
        Mockito.reset(spyImage);
        spyStorage.setRestoreFailedStorage(true);
        fsn.saveNamespace();
        checkEditExists(fsn, 1);
      }

      // Now shut down and restart the NN
      originalImage.close();
      fsn.close();
      fsn = null;

      // Start a new namesystem, which should be able to recover
      // the namespace from the previous incarnation.
      fsn = FSNamesystem.loadFromDisk(conf);

      // Make sure the image loaded including our edits.
      checkEditExists(fsn, 1);
    } finally {
      if (fsn != null) {
        fsn.close();
      }
    }
  }

};