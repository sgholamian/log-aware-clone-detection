//,temp,DefaultContainerExecutor.java,141,270,temp,DockerContainerExecutor.java,166,323
//,3
public class xxx {
  @Override
  public int launchContainer(ContainerStartContext ctx) throws IOException {
    Container container = ctx.getContainer();
    Path nmPrivateContainerScriptPath = ctx.getNmPrivateContainerScriptPath();
    Path nmPrivateTokensPath = ctx.getNmPrivateTokensPath();
    String userName = ctx.getUser();
    Path containerWorkDir = ctx.getContainerWorkDir();
    List<String> localDirs = ctx.getLocalDirs();
    List<String> logDirs = ctx.getLogDirs();

    //Variables for the launch environment can be injected from the command-line
    //while submitting the application
    String containerImageName = container.getLaunchContext().getEnvironment()
      .get(YarnConfiguration.NM_DOCKER_CONTAINER_EXECUTOR_IMAGE_NAME);
    if (LOG.isDebugEnabled()) {
      LOG.debug("containerImageName from launchContext: " + containerImageName);
    }
    Preconditions.checkArgument(!Strings.isNullOrEmpty(containerImageName),
      "Container image must not be null");
    containerImageName = containerImageName.replaceAll("['\"]", "");

    Preconditions.checkArgument(saneDockerImage(containerImageName), "Image: "
      + containerImageName + " is not a proper docker image");
    String dockerExecutor = getConf().get(
      YarnConfiguration.NM_DOCKER_CONTAINER_EXECUTOR_EXEC_NAME,
      YarnConfiguration.NM_DEFAULT_DOCKER_CONTAINER_EXECUTOR_EXEC_NAME);

    FsPermission dirPerm = new FsPermission(APPDIR_PERM);
    ContainerId containerId = container.getContainerId();

    // create container dirs on all disks
    String containerIdStr = ConverterUtils.toString(containerId);
    String appIdStr = ConverterUtils.toString(
      containerId.getApplicationAttemptId().getApplicationId());
    for (String sLocalDir : localDirs) {
      Path usersdir = new Path(sLocalDir, ContainerLocalizer.USERCACHE);
      Path userdir = new Path(usersdir, userName);
      Path appCacheDir = new Path(userdir, ContainerLocalizer.APPCACHE);
      Path appDir = new Path(appCacheDir, appIdStr);
      Path containerDir = new Path(appDir, containerIdStr);
      createDir(containerDir, dirPerm, true, userName);
    }

    // Create the container log-dirs on all disks
    createContainerLogDirs(appIdStr, containerIdStr, logDirs, userName);

    Path tmpDir = new Path(containerWorkDir,
      YarnConfiguration.DEFAULT_CONTAINER_TEMP_DIR);
    createDir(tmpDir, dirPerm, false, userName);

    // copy launch script to work dir
    Path launchDst =
      new Path(containerWorkDir, ContainerLaunch.CONTAINER_SCRIPT);
    lfs.util().copy(nmPrivateContainerScriptPath, launchDst);

    // copy container tokens to work dir
    Path tokenDst =
      new Path(containerWorkDir, ContainerLaunch.FINAL_CONTAINER_TOKENS_FILE);
    lfs.util().copy(nmPrivateTokensPath, tokenDst);

    String localDirMount = toMount(localDirs);
    String logDirMount = toMount(logDirs);
    String containerWorkDirMount = toMount(Collections.singletonList(
      containerWorkDir.toUri().getPath()));
    StringBuilder commands = new StringBuilder();
    //Use docker run to launch the docker container. See man pages for
    //docker-run
    //--rm removes the container automatically once the container finishes
    //--net=host allows the container to take on the host's network stack
    //--name sets the Docker Container name to the YARN containerId string
    //-v is used to bind mount volumes for local, log and work dirs.
    String commandStr = commands.append(dockerExecutor)
      .append(" ")
      .append("run")
      .append(" ")
      .append("--rm --net=host")
      .append(" ")
      .append(" --name " + containerIdStr)
      .append(localDirMount)
      .append(logDirMount)
      .append(containerWorkDirMount)
      .append(" ")
      .append(containerImageName)
      .toString();
    //Get the pid of the process which has been launched as a docker container
    //using docker inspect
    String dockerPidScript = "`" + dockerExecutor +
      " inspect --format {{.State.Pid}} " + containerIdStr + "`";

    // Create new local launch wrapper script
    LocalWrapperScriptBuilder sb = new UnixLocalWrapperScriptBuilder(
      containerWorkDir, commandStr, dockerPidScript);
    Path pidFile = getPidFilePath(containerId);
    if (pidFile != null) {
      sb.writeLocalWrapperScript(launchDst, pidFile);
    } else {
      //Although the container was activated by ContainerLaunch before exec()
      //was called, since then deactivateContainer() has been called.
      LOG.info("Container " + containerIdStr
          + " was marked as inactive. Returning terminated error");
      return ExitCode.TERMINATED.getExitCode();
    }
    
    ShellCommandExecutor shExec = null;
    try {
      lfs.setPermission(launchDst,
          ContainerExecutor.TASK_LAUNCH_SCRIPT_PERMISSION);
      lfs.setPermission(sb.getWrapperScriptPath(),
          ContainerExecutor.TASK_LAUNCH_SCRIPT_PERMISSION);

      // Setup command to run
      String[] command = getRunCommand(sb.getWrapperScriptPath().toString(),
        containerIdStr, userName, pidFile, this.getConf());
      if (LOG.isDebugEnabled()) {
        LOG.debug("launchContainer: " + commandStr + " " +
          Joiner.on(" ").join(command));
      }
      shExec = new ShellCommandExecutor(
        command,
        new File(containerWorkDir.toUri().getPath()),
        container.getLaunchContext().getEnvironment());      // sanitized env
      if (isContainerActive(containerId)) {
        shExec.execute();
      } else {
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
            + containerId + " and exit code: " + exitCode, e);
        logOutput(shExec.getOutput());
        String diagnostics = "Exception from container-launch: \n"
            + StringUtils.stringifyException(e) + "\n" + shExec.getOutput();
        container.handle(new ContainerDiagnosticsUpdateEvent(containerId,
            diagnostics));
      } else {
        container.handle(new ContainerDiagnosticsUpdateEvent(containerId,
            "Container killed on request. Exit code is " + exitCode));
      }
      return exitCode;
    } finally {
      if (shExec != null) {
        shExec.close();
      }
    }
    return 0;
  }

};