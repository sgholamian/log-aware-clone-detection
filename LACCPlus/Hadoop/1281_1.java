//,temp,TestHDFSServerPorts.java,364,394,temp,TestHDFSServerPorts.java,335,359
//,3
public class xxx {
    @Test(timeout = 300000)
    public void testBackupNodePorts() throws Exception {
      NameNode nn = null;
      try {
        nn = startNameNode();

        Configuration backup_config = new HdfsConfiguration(config);
        backup_config.set(
            DFSConfigKeys.DFS_NAMENODE_BACKUP_ADDRESS_KEY, THIS_HOST);
        // bind http server to the same port as name-node
        backup_config.set(DFSConfigKeys.DFS_NAMENODE_BACKUP_HTTP_ADDRESS_KEY, 
            backup_config.get(DFSConfigKeys.DFS_NAMENODE_HTTP_ADDRESS_KEY));

        LOG.info("= Starting 1 on: " + backup_config.get(
            DFSConfigKeys.DFS_NAMENODE_BACKUP_HTTP_ADDRESS_KEY));

        assertFalse("Backup started on same port as Namenode", 
                           canStartBackupNode(backup_config)); // should fail

        // bind http server to a different port
        backup_config.set(
            DFSConfigKeys.DFS_NAMENODE_BACKUP_HTTP_ADDRESS_KEY, THIS_HOST);
        LOG.info("= Starting 2 on: " + backup_config.get(
            DFSConfigKeys.DFS_NAMENODE_BACKUP_HTTP_ADDRESS_KEY));

        boolean started = canStartBackupNode(backup_config);
        assertTrue("Backup Namenode should've started", started); // should start now
      } finally {
        stopNameNode(nn);
      }
  }

};