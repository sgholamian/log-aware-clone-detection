//,temp,TestStartup.java,337,355,temp,TestStartup.java,311,330
//,3
public class xxx {
  @Test
  public void testChkpointStartup2() throws IOException{
    LOG.info("--starting checkpointStartup2 - same directory for checkpoint");
    // different name dirs
    config.set(DFSConfigKeys.DFS_NAMENODE_NAME_DIR_KEY,
        fileAsURI(new File(hdfsDir, "name")).toString());
    config.set(DFSConfigKeys.DFS_NAMENODE_EDITS_DIR_KEY,
        fileAsURI(new File(hdfsDir, "edits")).toString());
    // same checkpoint dirs
    config.set(DFSConfigKeys.DFS_NAMENODE_CHECKPOINT_EDITS_DIR_KEY,
        fileAsURI(new File(hdfsDir, "chkpt")).toString());
    config.set(DFSConfigKeys.DFS_NAMENODE_CHECKPOINT_DIR_KEY,
        fileAsURI(new File(hdfsDir, "chkpt")).toString());

    createCheckPoint(1);

    corruptNameNodeFiles();
    checkNameNodeFiles();

  }

};