//,temp,MapredLocalTask.java,164,368,temp,MapRedTask.java,93,334
//,3
public class xxx {
  @Override
  public int execute() {

    boolean ctxCreated = false;
    Context ctx = context;

    try {
      if (ctx == null) {
        ctx = new Context(conf);
        ctxCreated = true;
      }

      // estimate number of reducers
      setNumberOfReducers();

      // auto-determine local mode if allowed
      if (!ctx.isLocalOnlyExecutionMode() &&
          conf.getBoolVar(HiveConf.ConfVars.LOCALMODEAUTO)) {

        if (inputSummary == null) {
          inputSummary = Utilities.getInputSummary(ctx, work.getMapWork(), null);
        }

        // set the values of totalInputFileSize and totalInputNumFiles, estimating them
        // if percentage block sampling is being used
        double samplePercentage = Utilities.getHighestSamplePercentage(work.getMapWork());
        totalInputFileSize = Utilities.getTotalInputFileSize(inputSummary, work.getMapWork(), samplePercentage);
        totalInputNumFiles = Utilities.getTotalInputNumFiles(inputSummary, work.getMapWork(), samplePercentage);

        // at this point the number of reducers is precisely defined in the plan
        int numReducers = work.getReduceWork() == null ? 0 : work.getReduceWork().getNumReduceTasks();

        if (LOG.isDebugEnabled()) {
          LOG.debug("Task: " + getId() + ", Summary: " +
                    totalInputFileSize + "," + totalInputNumFiles + ","
                    + numReducers);
        }

        String reason = MapRedTask.isEligibleForLocalMode(conf, numReducers,
            totalInputFileSize, totalInputNumFiles);
        if (reason == null) {
          // clone configuration before modifying it on per-task basis
          cloneConf();
          ShimLoader.getHadoopShims().setJobLauncherRpcAddress(conf, "local");
          console.printInfo("Selecting local mode for task: " + getId());
          this.setLocalMode(true);
        } else {
          console.printInfo("Cannot run job locally: " + reason);
          this.setLocalMode(false);
        }
      }

      runningViaChild = conf.getBoolVar(HiveConf.ConfVars.SUBMITVIACHILD);

      if (!runningViaChild) {
        // since we are running the mapred task in the same jvm, we should update the job conf
        // in ExecDriver as well to have proper local properties.
        if (this.isLocalMode()) {
          // save the original job tracker
          ctx.setOriginalTracker(ShimLoader.getHadoopShims().getJobLauncherRpcAddress(job));
          // change it to local
          ShimLoader.getHadoopShims().setJobLauncherRpcAddress(job, "local");
        }
        // we are not running this mapred task via child jvm
        // so directly invoke ExecDriver
        int ret = super.execute();

        // restore the previous properties for framework name, RM address etc.
        if (this.isLocalMode()) {
          // restore the local job tracker back to original
          ctx.restoreOriginalTracker();
        }
        return ret;
      }

      // we need to edit the configuration to setup cmdline. clone it first
      cloneConf();

      // propagate input format if necessary
      super.setInputAttributes(conf);

      // enable assertion
      String hadoopExec = conf.getVar(HiveConf.ConfVars.HADOOPBIN);
      String hiveJar = conf.getJar();

      String libJars = super.getResource(conf, ResourceType.JAR);
      String libJarsOption = StringUtils.isEmpty(libJars) ? " " : " -libjars " + libJars + " ";

      // Generate the hiveConfArgs after potentially adding the jars
      String hiveConfArgs = generateCmdLine(conf, ctx);

      // write out the plan to a local file
      Path planPath = new Path(ctx.getLocalTmpPath(), "plan.xml");
      MapredWork plan = getWork();
      LOG.info("Generating plan file " + planPath.toString());

      OutputStream out = null;
      try {
        out = FileSystem.getLocal(conf).create(planPath);
        SerializationUtilities.serializePlan(plan, out);
        out.close();
        out = null;
      } finally {
        IOUtils.closeQuietly(out);
      }

      String isSilent = "true".equalsIgnoreCase(System
          .getProperty("test.silent")) ? "-nolog" : "";

      String jarCmd = hiveJar + " " + ExecDriver.class.getName() + libJarsOption;

      String cmdLine = hadoopExec + " jar " + jarCmd + " -plan "
          + planPath.toString() + " " + isSilent + " " + hiveConfArgs;

      String workDir = (new File(".")).getCanonicalPath();

      String files = super.getResource(conf, ResourceType.FILE);
      if (!files.isEmpty()) {
        cmdLine = cmdLine + " -files " + files;

        workDir = ctx.getLocalTmpPath().toUri().getPath();

        if (! (new File(workDir)).mkdir()) {
          throw new IOException ("Cannot create tmp working dir: " + workDir);
        }

        for (String f: StringUtils.split(files, ',')) {
          Path p = new Path(f);
          String target = p.toUri().getPath();
          String link = workDir + Path.SEPARATOR + p.getName();
          if (FileUtil.symLink(target, link) != 0) {
            throw new IOException ("Cannot link to added file: " + target + " from: " + link);
          }
        }
      }

      LOG.info("Executing: " + cmdLine);

      // Inherit Java system variables
      String hadoopOpts;
      StringBuilder sb = new StringBuilder();
      Properties p = System.getProperties();
      for (String element : HIVE_SYS_PROP) {
        if (p.containsKey(element)) {
          sb.append(" -D" + element + "=" + p.getProperty(element));
        }
      }
      hadoopOpts = sb.toString();
      // Inherit the environment variables
      String[] env;
      Map<String, String> variables = new HashMap<String, String>(System.getenv());
      // The user can specify the hadoop memory

      if (ShimLoader.getHadoopShims().isLocalMode(conf)) {
        // if we are running in local mode - then the amount of memory used
        // by the child jvm can no longer default to the memory used by the
        // parent jvm
        int hadoopMem = conf.getIntVar(HiveConf.ConfVars.HIVEHADOOPMAXMEM);
        if (hadoopMem == 0) {
          // remove env var that would default child jvm to use parent's memory
          // as default. child jvm would use default memory for a hadoop client
          variables.remove(HADOOP_MEM_KEY);
        } else {
          // user specified the memory for local mode hadoop run
          variables.put(HADOOP_MEM_KEY, String.valueOf(hadoopMem));
        }
      } else {
        // nothing to do - we are not running in local mode - only submitting
        // the job via a child process. in this case it's appropriate that the
        // child jvm use the same memory as the parent jvm
      }

      if (variables.containsKey(HADOOP_OPTS_KEY)) {
        variables.put(HADOOP_OPTS_KEY, variables.get(HADOOP_OPTS_KEY)
            + hadoopOpts);
      } else {
        variables.put(HADOOP_OPTS_KEY, hadoopOpts);
      }

      if(variables.containsKey(HIVE_DEBUG_RECURSIVE)) {
        configureDebugVariablesForChildJVM(variables);
      }

      if (PROXY == Utils.getUGI().getAuthenticationMethod()) {
        variables.put(HADOOP_PROXY_USER, Utils.getUGI().getShortUserName());
      }

      env = new String[variables.size()];
      int pos = 0;
      for (Map.Entry<String, String> entry : variables.entrySet()) {
        String name = entry.getKey();
        String value = entry.getValue();
        env[pos++] = name + "=" + value;
      }
      // Run ExecDriver in another JVM
      executor = spawn(cmdLine, workDir, env);

      CachingPrintStream errPrintStream =
          new CachingPrintStream(SessionState.getConsole().getChildErrStream());

      StreamPrinter outPrinter = new StreamPrinter(
          executor.getInputStream(), null,
          SessionState.getConsole().getChildOutStream());
      StreamPrinter errPrinter = new StreamPrinter(
          executor.getErrorStream(), null,
          errPrintStream);

      outPrinter.start();
      errPrinter.start();

      int exitVal = jobExecHelper.progressLocal(executor, getId());

      // wait for stream threads to finish
      outPrinter.join();
      errPrinter.join();

      if (exitVal != 0) {
        LOG.error("Execution failed with exit status: " + exitVal);
        if (SessionState.get() != null) {
          SessionState.get().addLocalMapRedErrors(getId(), errPrintStream.getOutput());
        }
      } else {
        LOG.info("Execution completed successfully");
      }

      return exitVal;
    } catch (Exception e) {
      LOG.error("Got exception", e);
      return (1);
    } finally {
      try {
        // creating the context can create a bunch of files. So make
        // sure to clear it out
        if (ctxCreated) {
          ctx.clear();
        }

      } catch (Exception e) {
        LOG.error("Exception: ", e);
      }
    }
  }

};