//,temp,MapredLocalTask.java,164,368,temp,MapRedTask.java,93,334
//,3
public class xxx {
  private int executeInChildVM() {
    // execute in child jvm
    try {
      // generate the cmd line to run in the child jvm
      String hiveJar = conf.getJar();

      String hadoopExec = conf.getVar(HiveConf.ConfVars.HADOOPBIN);
      conf.setVar(ConfVars.HIVEADDEDJARS, Utilities.getResourceFiles(conf, SessionState.ResourceType.JAR));
      // write out the plan to a local file
      Path planPath = new Path(context.getLocalTmpPath(), "plan.xml");
      MapredLocalWork plan = getWork();
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

      String isSilent = "true".equalsIgnoreCase(System.getProperty("test.silent")) ? "-nolog" : "";

      String libJars = ExecDriver.getResource(conf, ResourceType.JAR);
      String libJarsOption = StringUtils.isEmpty(libJars) ? " " : " -libjars " + libJars + " ";

      String jarCmd = hiveJar + " " + ExecDriver.class.getName() + libJarsOption;
      String hiveConfArgs = ExecDriver.generateCmdLine(conf, context);
      String cmdLine = hadoopExec + " jar " + jarCmd + " -localtask -plan " + planPath.toString()
          + " " + isSilent + " " + hiveConfArgs;

      String workDir = (new File(".")).getCanonicalPath();
      String files = Utilities.getResourceFiles(conf, SessionState.ResourceType.FILE);

      if (!files.isEmpty()) {
        cmdLine = cmdLine + " -files " + files;

        workDir = context.getLocalTmpPath().toUri().getPath();

        if (!(new File(workDir)).mkdir()) {
          throw new IOException("Cannot create tmp working dir: " + workDir);
        }

        for (String f : StringUtils.split(files, ',')) {
          Path p = new Path(f);
          String target = p.toUri().getPath();
          String link = workDir + Path.SEPARATOR + p.getName();
          if (FileUtil.symLink(target, link) != 0) {
            throw new IOException("Cannot link to added file: " + target + " from: " + link);
          }
        }
      }

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

      // if ("local".equals(conf.getVar(HiveConf.ConfVars.HADOOPJT))) {
      // if we are running in local mode - then the amount of memory used
      // by the child jvm can no longer default to the memory used by the
      // parent jvm
      // int hadoopMem = conf.getIntVar(HiveConf.ConfVars.HIVEHADOOPMAXMEM);
      int hadoopMem = conf.getIntVar(HiveConf.ConfVars.HIVEHADOOPMAXMEM);
      if (hadoopMem == 0) {
        // remove env var that would default child jvm to use parent's memory
        // as default. child jvm would use default memory for a hadoop client
        variables.remove(HADOOP_MEM_KEY);
      } else {
        // user specified the memory for local mode hadoop run
        console.printInfo(" set heap size\t" + hadoopMem + "MB");
        variables.put(HADOOP_MEM_KEY, String.valueOf(hadoopMem));
      }
      // } else {
      // nothing to do - we are not running in local mode - only submitting
      // the job via a child process. in this case it's appropriate that the
      // child jvm use the same memory as the parent jvm

      // }

      //Set HADOOP_USER_NAME env variable for child process, so that
      // it also runs with hadoop permissions for the user the job is running as
      // This will be used by hadoop only in unsecure(/non kerberos) mode
      String endUserName = Utils.getUGI().getShortUserName();
      LOG.debug("setting HADOOP_USER_NAME\t" + endUserName);
      variables.put("HADOOP_USER_NAME", endUserName);

      if (variables.containsKey(HADOOP_OPTS_KEY)) {
        variables.put(HADOOP_OPTS_KEY, variables.get(HADOOP_OPTS_KEY) + hadoopOpts);
      } else {
        variables.put(HADOOP_OPTS_KEY, hadoopOpts);
      }

      //For Windows OS, we need to pass HIVE_HADOOP_CLASSPATH Java parameter while starting
      //Hiveserver2 using "-hiveconf hive.hadoop.classpath=%HIVE_LIB%". This is to combine path(s).
      if (HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_HADOOP_CLASSPATH)!= null)
      {
        if (variables.containsKey("HADOOP_CLASSPATH"))
        {
          variables.put("HADOOP_CLASSPATH", variables.get("HADOOP_CLASSPATH") + ";" + HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_HADOOP_CLASSPATH));
        } else {
          variables.put("HADOOP_CLASSPATH", HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_HADOOP_CLASSPATH));
        }
      }

      if(variables.containsKey(MapRedTask.HIVE_DEBUG_RECURSIVE)) {
        MapRedTask.configureDebugVariablesForChildJVM(variables);
      }


      if(UserGroupInformation.isSecurityEnabled() &&
           UserGroupInformation.isLoginKeytabBased()) {
        //If kerberos security is enabled, and HS2 doAs is enabled,
        // then additional params need to be set so that the command is run as
        // intended user
        secureDoAs = new SecureCmdDoAs(conf);
        secureDoAs.addEnv(variables);
      }

      // If HIVE_LOCAL_TASK_CHILD_OPTS is set, child VM environment setting
      // HADOOP_CLIENT_OPTS will be replaced with HIVE_LOCAL_TASK_CHILD_OPTS.
      // HADOOP_OPTS is updated too since HADOOP_CLIENT_OPTS is appended
      // to HADOOP_OPTS in most cases. This way, the local task JVM can
      // have different settings from those of HiveServer2.
      if (variables.containsKey(HIVE_LOCAL_TASK_CHILD_OPTS_KEY)) {
        String childOpts = variables.get(HIVE_LOCAL_TASK_CHILD_OPTS_KEY);
        if (childOpts == null) {
          childOpts = "";
        }
        String clientOpts = variables.put(HADOOP_CLIENT_OPTS, childOpts);
        String tmp = variables.get(HADOOP_OPTS_KEY);
        if (tmp != null && !StringUtils.isBlank(clientOpts)) {
          tmp = tmp.replace(clientOpts, childOpts);
          variables.put(HADOOP_OPTS_KEY, tmp);
        }
      }

      env = new String[variables.size()];
      int pos = 0;
      for (Map.Entry<String, String> entry : variables.entrySet()) {
        String name = entry.getKey();
        String value = entry.getValue();
        env[pos++] = name + "=" + value;
        LOG.debug("Setting env: " + name + "=" + LogUtils.maskIfPassword(name, value));
      }

      LOG.info("Executing: " + cmdLine);

      // Run ExecDriver in another JVM
      executor = Runtime.getRuntime().exec(cmdLine, env, new File(workDir));

      final LogRedirector.LogSourceCallback callback = () -> {return executor.isAlive();};

      LogRedirector.redirect(
          Thread.currentThread().getName() + "-LocalTask-" + getName() + "-stdout",
          new LogRedirector(executor.getInputStream(), LOG, callback));
      LogRedirector.redirect(
          Thread.currentThread().getName() + "-LocalTask-" + getName() + "-stderr",
          new LogRedirector(executor.getErrorStream(), LOG, callback));

      CachingPrintStream errPrintStream = new CachingPrintStream(System.err);

      StreamPrinter outPrinter = new StreamPrinter(executor.getInputStream(), null, System.out);
      StreamPrinter errPrinter = new StreamPrinter(executor.getErrorStream(), null, errPrintStream);

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
      LOG.error("Exception: ", e);
      return (1);
    } finally {
      if (secureDoAs != null) {
        secureDoAs.close();
      }
    }
  }

};