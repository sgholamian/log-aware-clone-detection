//,temp,LinuxContainerExecutor.java,213,269,temp,WindowsSecureContainerExecutor.java,644,729
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
    
    verifyUsernamePattern(user);
    String runAsUser = getRunAsUser(user);
    List<String> command = new ArrayList<String>();
    addSchedPriorityCommand(command);
    command.addAll(Arrays.asList(containerExecutorExe, 
                   runAsUser,
                   user, 
                   Integer.toString(PrivilegedOperation.RunAsUserCommand.INITIALIZE_CONTAINER.getValue()),
                   appId,
                   nmPrivateContainerTokensPath.toUri().getPath().toString(),
                   StringUtils.join(PrivilegedOperation.LINUX_FILE_PATH_SEPARATOR,
                       localDirs),
                   StringUtils.join(PrivilegedOperation.LINUX_FILE_PATH_SEPARATOR,
                       logDirs)));

    File jvm =                                  // use same jvm as parent
      new File(new File(System.getProperty("java.home"), "bin"), "java");
    command.add(jvm.toString());
    command.add("-classpath");
    command.add(System.getProperty("java.class.path"));
    String javaLibPath = System.getProperty("java.library.path");
    if (javaLibPath != null) {
      command.add("-Djava.library.path=" + javaLibPath);
    }
    command.addAll(ContainerLocalizer.getJavaOpts(getConf()));
    buildMainArgs(command, user, appId, locId, nmAddr, localDirs);
    String[] commandArray = command.toArray(new String[command.size()]);
    ShellCommandExecutor shExec = new ShellCommandExecutor(commandArray);
    if (LOG.isDebugEnabled()) {
      LOG.debug("initApplication: " + Arrays.toString(commandArray));
    }
    try {
      shExec.execute();
      if (LOG.isDebugEnabled()) {
        logOutput(shExec.getOutput());
      }
    } catch (ExitCodeException e) {
      int exitCode = shExec.getExitCode();
      LOG.warn("Exit code from container " + locId + " startLocalizer is : "
          + exitCode, e);
      logOutput(shExec.getOutput());
      throw new IOException("Application " + appId + " initialization failed" +
      		" (exitCode=" + exitCode + ") with output: " + shExec.getOutput(), e);
    }
  }

};