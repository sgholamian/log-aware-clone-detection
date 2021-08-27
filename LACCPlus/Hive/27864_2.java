//,temp,ReplDumpTask.java,233,249,temp,ReplLoadTask.java,167,184
//,3
public class xxx {
  private void initiateAuthorizationLoadTask() throws SemanticException {
    if (RANGER_AUTHORIZER.equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.REPL_AUTHORIZATION_PROVIDER_SERVICE))) {
      Path rangerLoadRoot = new Path(new Path(work.dumpDirectory).getParent(), ReplUtils.REPL_RANGER_BASE_DIR);
      LOG.info("Adding Import Ranger Metadata Task from {} ", rangerLoadRoot);
      String targetDbName = StringUtils.isEmpty(work.dbNameToLoadIn) ? work.getSourceDbName() : work.dbNameToLoadIn;
      RangerLoadWork rangerLoadWork = new RangerLoadWork(rangerLoadRoot, work.getSourceDbName(), targetDbName,
          work.getMetricCollector());
      Task<RangerLoadWork> rangerLoadTask = TaskFactory.get(rangerLoadWork, conf);
      if (childTasks == null) {
        childTasks = new ArrayList<>();
      }
      childTasks.add(rangerLoadTask);
    } else {
      throw new SemanticException(ErrorMsg.REPL_INVALID_CONFIG_FOR_SERVICE.format("Authorizer " +
        conf.getVar(HiveConf.ConfVars.REPL_AUTHORIZATION_PROVIDER_SERVICE)
              + " not supported for replication ", ReplUtils.REPL_RANGER_SERVICE));
    }
  }

};