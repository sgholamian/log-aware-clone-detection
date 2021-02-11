//,temp,TestContainerManagerRecovery.java,129,163,temp,BaseContainerManagerTest.java,192,232
//,3
public class xxx {
  @Override
  @Before
  public void setup() throws IOException {
    localFS.delete(new Path(localDir.getAbsolutePath()), true);
    localFS.delete(new Path(tmpDir.getAbsolutePath()), true);
    localFS.delete(new Path(localLogDir.getAbsolutePath()), true);
    localFS.delete(new Path(remoteLogDir.getAbsolutePath()), true);
    localDir.mkdir();
    tmpDir.mkdir();
    localLogDir.mkdir();
    remoteLogDir.mkdir();
    LOG.info("Created localDir in " + localDir.getAbsolutePath());
    LOG.info("Created tmpDir in " + tmpDir.getAbsolutePath());

    String bindAddress = "0.0.0.0:"+ServerSocketUtil.getPort(49160, 10);
    conf.set(YarnConfiguration.NM_ADDRESS, bindAddress);
    conf.set(YarnConfiguration.NM_LOCAL_DIRS, localDir.getAbsolutePath());
    conf.set(YarnConfiguration.NM_LOG_DIRS, localLogDir.getAbsolutePath());
    conf.set(YarnConfiguration.NM_REMOTE_APP_LOG_DIR, remoteLogDir.getAbsolutePath());
    conf.setLong(YarnConfiguration.NM_LOG_RETAIN_SECONDS, 1);

    // enable atsv2 by default in test
    conf.setBoolean(YarnConfiguration.TIMELINE_SERVICE_ENABLED, true);
    conf.setFloat(YarnConfiguration.TIMELINE_SERVICE_VERSION, 2.0f);

    // Default delSrvc
    delSrvc = createDeletionService();
    delSrvc.init(conf);
    exec = createContainerExecutor();
    dirsHandler = new LocalDirsHandlerService();
    nodeHealthChecker = new NodeHealthCheckerService(
        NodeManager.getNodeHealthScriptRunner(conf), dirsHandler);
    nodeHealthChecker.init(conf);

  }

};