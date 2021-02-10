//,temp,TestContainerManagerRecovery.java,129,163,temp,BaseContainerManagerTest.java,192,232
//,3
public class xxx {
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

    String bindAddress = "0.0.0.0:" + ServerSocketUtil.getPort(49162, 10);
    conf.set(YarnConfiguration.NM_ADDRESS, bindAddress);
    conf.set(YarnConfiguration.NM_LOCAL_DIRS, localDir.getAbsolutePath());
    conf.set(YarnConfiguration.NM_LOG_DIRS, localLogDir.getAbsolutePath());
    conf.set(YarnConfiguration.NM_REMOTE_APP_LOG_DIR, remoteLogDir.getAbsolutePath());
    conf.set(YarnConfiguration.NM_LOCALIZER_ADDRESS, "0.0.0.0:"
        + ServerSocketUtil.getPort(8040, 10));


    conf.setLong(YarnConfiguration.NM_LOG_RETAIN_SECONDS, 1);
    // Default delSrvc
    exec = createContainerExecutor();
    delSrvc = createDeletionService();
    delSrvc.init(conf);

    dirsHandler = new LocalDirsHandlerService();
    nodeHealthChecker = new NodeHealthCheckerService(
        NodeManager.getNodeHealthScriptRunner(conf), dirsHandler);
    nodeHealthChecker.init(conf);
    containerManager = createContainerManager(delSrvc);
    ((NMContext)context).setContainerManager(containerManager);
    nodeStatusUpdater.init(conf);
    containerManager.init(conf);
    nodeStatusUpdater.start();
    ((NMContext)context).setNodeStatusUpdater(nodeStatusUpdater);
    ((NMContext)context).setContainerStateTransitionListener(
        new NodeManager.DefaultContainerStateListener());
  }

};