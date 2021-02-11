//,temp,DefaultContainerExecutor.java,141,270,temp,DockerContainerExecutor.java,166,323
//,3
public class xxx {
  @Override
  public int launchContainer(ContainerStartContext ctx) throws IOException {
    Container container = ctx.getContainer();
    Path nmPrivateContainerScriptPath = ctx.getNmPrivateContainerScriptPath();
    Path nmPrivateTokensPath = ctx.getNmPrivateTokensPath();
    String user = ctx.getUser();
    Path containerWorkDir = ctx.getContainerWorkDir();
    List<String> localDirs = ctx.getLocalDirs();
    List<String> logDirs = ctx.getLogDirs();

    FsPermission dirPerm = new FsPermission(APPDIR_PERM);
    ContainerId containerId = container.getContainerId();

    // create container dirs on all disks
    String containerIdStr = ConverterUtils.toString(containerId);
    String appIdStr =
        ConverterUtils.toString(
            containerId.getApplicationAttemptId().
                getApplicationId());
    for (String sLocalDir : localDirs) {
      Path usersdir = new Path(sLocalDir, ContainerLocalizer.USERCACHE);
      Path userdir = new Path(usersdir, user);
      Path appCacheDir = new Path(userdir, ContainerLocalizer.APPCACHE);
      Path appDir = new Path(appCacheDir, appIdStr);
      Path containerDir = new Path(appDir, containerIdStr);
      createDir(containerDir, dirPerm, true, user);
    }

    // Create the container log-dirs on all disks
    createContainerLogDirs(appIdStr, containerIdStr, logDirs, user);

    Path tmpDir = new Path(containerWorkDir,
        YarnConfiguration.DEFAULT_CONTAINER_TEMP_DIR);
    createDir(tmpDir, dirPerm, false, user);


    // copy container tokens to work dir
    Path tokenDst =
      new Path(containerWorkDir, ContainerLaunch.FINAL_CONTAINER_TOKENS_FILE);
    copyFile(nmPrivateTokensPath, tokenDst, user);

    // copy launch script to work dir
    Path launchDst =
        new Path(containerWorkDir, ContainerLaunch.CONTAINER_SCRIPT);
    copyFile(nmPrivateContainerScriptPath, launchDst, user);

    // Create new local launch wrapper script
    LocalWrapperScriptBuilder sb = getLocalWrapperScriptBuilder(
        containerIdStr, containerWorkDir); 

    // Fail fast if attempting to launch the wrapper script would fail due to
    // Windows path length limitation.
    if (Shell.WINDOWS &&
        sb.getWrapperScriptPath().toString().length() > WIN_MAX_PATH) {
      throw new IOException(String.format(
        "Cannot launch container using script at path %s, because it exceeds " +
        "the maximum supported path length of %d characters.  Consider " +
        "configuring shorter directories in %s.", sb.getWrapperScriptPath(),
        WIN_MAX_PATH, YarnConfiguration.NM_LOCAL_DIRS));
    }

    Path pidFile = getPidFilePath(containerId);
    if (pidFile != null) {
      sb.writeLocalWrapperScript(launchDst, pidFile);
    } else {
      LOG.info("Container " + containerIdStr
          + " was marked as inactive. Returning terminated error");
      return ExitCode.TERMINATED.getExitCode();
    }
    
    // create log dir under app
    // fork script
    Shell.CommandExecutor shExec = null;
    try {
      setScriptExecutable(launchDst, user);
      setScriptExecutable(sb.getWrapperScriptPath(), user);

      shExec = buildCommandExecutor(sb.getWrapperScriptPath().toString(),
          containerIdStr, user, pidFile, container.getResource(),
          new File(containerWorkDir.toUri().getPath()),
          container.getLaunchContext().getEnvironment());
      
      if (isContainerActive(containerId)) {
        shExec.execute();
      }
      else {
        LOG.info("Container " + containerIdStr +
            " was marked as inactive. Returning terminated error");
        return ExitCode.TERMINATED.getExitCode();
      }
    } catch (IOException e) {
      if (null == shExec) {
        return -1;
      }
      int exitCode = shExec.getExitCode();
      LOG.warn("Exit code from container " + containerId + " is : " + exitCode);
      // 143 (SIGTERM) and 137 (SIGKILL) exit codes means the container was
      // terminated/killed forcefully. In all other cases, log the
      // container-executor's output
      if (exitCode != ExitCode.FORCE_KILLED.getExitCode()
          && exitCode != ExitCode.TERMINATED.getExitCode()) {
        LOG.warn("Exception from container-launch with container ID: "
            + containerId + " and exit code: " + exitCode , e);

        StringBuilder builder = new StringBuilder();
        builder.append("Exception from container-launch.\n");
        builder.append("Container id: " + containerId + "\n");
        builder.append("Exit code: " + exitCode + "\n");
        if (!Optional.fromNullable(e.getMessage()).or("").isEmpty()) {
          builder.append("Exception message: " + e.getMessage() + "\n");
        }
        builder.append("Stack trace: "
            + StringUtils.stringifyException(e) + "\n");
        if (!shExec.getOutput().isEmpty()) {
          builder.append("Shell output: " + shExec.getOutput() + "\n");
        }
        String diagnostics = builder.toString();
        logOutput(diagnostics);
        container.handle(new ContainerDiagnosticsUpdateEvent(containerId,
            diagnostics));
      } else {
        container.handle(new ContainerDiagnosticsUpdateEvent(containerId,
            "Container killed on request. Exit code is " + exitCode));
      }
      return exitCode;
    } finally {
      if (shExec != null) shExec.close();
    }
    return 0;
  }

};