//,temp,LinuxContainerExecutor.java,213,269,temp,WindowsSecureContainerExecutor.java,644,729
//,3
public class xxx {
  @Override
  public void startLocalizer(LocalizerStartContext ctx) throws IOException,
      InterruptedException {
    Path nmPrivateContainerTokensPath = ctx.getNmPrivateContainerTokens();
    InetSocketAddress nmAddr = ctx.getNmAddr();
    String user = ctx.getUser();
    String appId = ctx.getAppId();
    String locId = ctx.getLocId();
    LocalDirsHandlerService dirsHandler = ctx.getDirsHandler();
    List<String> localDirs = dirsHandler.getLocalDirs();
    List<String> logDirs = dirsHandler.getLogDirs();

    Path classpathJarPrivateDir = dirsHandler.getLocalPathForWrite(
        ResourceLocalizationService.NM_PRIVATE_DIR);
    createUserLocalDirs(localDirs, user);
    createUserCacheDirs(localDirs, user);
    createAppDirs(localDirs, user, appId);
    createAppLogDirs(appId, logDirs, user);

    Path appStorageDir = getWorkingDir(localDirs, user, appId);

    String tokenFn = String.format(
        ContainerLocalizer.TOKEN_FILE_NAME_FMT, locId);
    Path tokenDst = new Path(appStorageDir, tokenFn);
    copyFile(nmPrivateContainerTokensPath, tokenDst, user);

    File cwdApp = new File(appStorageDir.toString());
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("cwdApp: %s", cwdApp));
    }

    List<String> command ;

    command = new ArrayList<String>();

    //use same jvm as parent
    File jvm = new File(
        new File(System.getProperty("java.home"), "bin"), "java.exe");
    command.add(jvm.toString());

    Path cwdPath = new Path(cwdApp.getPath());

    // Build a temp classpath jar. See ContainerLaunch.sanitizeEnv().
    // Passing CLASSPATH explicitly is *way* too long for command line.
    String classPath = System.getProperty("java.class.path");
    Map<String, String> env = new HashMap<String, String>(System.getenv());
    String jarCp[] = FileUtil.createJarWithClassPath(classPath,
        classpathJarPrivateDir, cwdPath, env);
    String classPathJar = localizeClasspathJar(
        new Path(jarCp[0]), cwdPath, user).toString();
    command.add("-classpath");
    command.add(classPathJar + jarCp[1]);

    String javaLibPath = System.getProperty("java.library.path");
    if (javaLibPath != null) {
      command.add("-Djava.library.path=" + javaLibPath);
    }
    command.addAll(ContainerLocalizer.getJavaOpts(getConf()));

    ContainerLocalizer.buildMainArgs(command, user, appId, locId, nmAddr,
        localDirs);

    String cmdLine = StringUtils.join(command, " ");

    String localizerPid = String.format(LOCALIZER_PID_FORMAT, locId);

    WintuilsProcessStubExecutor stubExecutor = new WintuilsProcessStubExecutor(
        cwdApp.getAbsolutePath(),
        localizerPid, user, "nul:", cmdLine);
    try {
      stubExecutor.execute();
      stubExecutor.validateResult();
    } finally {
      stubExecutor.close();
      try
      {
        killContainer(localizerPid, Signal.KILL);
      }
      catch(Throwable e) {
        LOG.warn(String.format(
            "An exception occured during the cleanup of localizer job %s:%n%s",
            localizerPid,
            org.apache.hadoop.util.StringUtils.stringifyException(e)));
      }
    }
  }

};