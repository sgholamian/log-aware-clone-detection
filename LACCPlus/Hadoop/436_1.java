//,temp,DefaultContainerExecutor.java,101,139,temp,DockerContainerExecutor.java,130,163
//,3
public class xxx {
  @Override
  public void startLocalizer(LocalizerStartContext ctx)
      throws IOException, InterruptedException {
    Path nmPrivateContainerTokensPath = ctx.getNmPrivateContainerTokens();
    InetSocketAddress nmAddr = ctx.getNmAddr();
    String user = ctx.getUser();
    String appId = ctx.getAppId();
    String locId = ctx.getLocId();
    LocalDirsHandlerService dirsHandler = ctx.getDirsHandler();

    List<String> localDirs = dirsHandler.getLocalDirs();
    List<String> logDirs = dirsHandler.getLogDirs();
    
    createUserLocalDirs(localDirs, user);
    createUserCacheDirs(localDirs, user);
    createAppDirs(localDirs, user, appId);
    createAppLogDirs(appId, logDirs, user);

    // randomly choose the local directory
    Path appStorageDir = getWorkingDir(localDirs, user, appId);

    String tokenFn = String.format(ContainerLocalizer.TOKEN_FILE_NAME_FMT, locId);
    Path tokenDst = new Path(appStorageDir, tokenFn);
    copyFile(nmPrivateContainerTokensPath, tokenDst, user);
    LOG.info("Copying from " + nmPrivateContainerTokensPath + " to " + tokenDst);


    FileContext localizerFc = FileContext.getFileContext(
        lfs.getDefaultFileSystem(), getConf());
    localizerFc.setUMask(lfs.getUMask());
    localizerFc.setWorkingDirectory(appStorageDir);
    LOG.info("Localizer CWD set to " + appStorageDir + " = " 
        + localizerFc.getWorkingDirectory());
    ContainerLocalizer localizer =
        new ContainerLocalizer(localizerFc, user, appId, locId, 
            getPaths(localDirs), RecordFactoryProvider.getRecordFactory(getConf()));
    // TODO: DO it over RPC for maintaining similarity?
    localizer.runLocalization(nmAddr);
  }

};