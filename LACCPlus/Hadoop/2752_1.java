//,temp,TestMaintenanceState.java,908,922,temp,TestMaintenanceState.java,269,286
//,3
public class xxx {
  @Test(timeout = 360000)
  public void testEnterMaintenanceWhenFileOpen() throws Exception {
    LOG.info("Starting testEnterMaintenanceWhenFileOpen");
    startCluster(1, 3);

    final Path file = new Path("/testEnterMaintenanceWhenFileOpen.dat");

    final FileSystem fileSys = getCluster().getFileSystem(0);
    writeIncompleteFile(fileSys, file, (short)3, (short)2);

    takeNodeOutofService(0, null, Long.MAX_VALUE, null,
        AdminStates.IN_MAINTENANCE);

    cleanupFile(fileSys, file);
  }

};