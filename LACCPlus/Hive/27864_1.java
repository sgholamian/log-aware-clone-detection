//,temp,ReplDumpTask.java,233,249,temp,ReplLoadTask.java,167,184
//,3
public class xxx {
  private void initiateAuthorizationDumpTask() throws SemanticException {
    if (RANGER_AUTHORIZER.equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.REPL_AUTHORIZATION_PROVIDER_SERVICE))) {
      Path rangerDumpRoot = new Path(work.getCurrentDumpPath(), ReplUtils.REPL_RANGER_BASE_DIR);
      LOG.info("Exporting Authorization Metadata from {} at {} ", RANGER_AUTHORIZER, rangerDumpRoot);
      RangerDumpWork rangerDumpWork = new RangerDumpWork(rangerDumpRoot, work.dbNameOrPattern,
          work.getMetricCollector());
      Task<RangerDumpWork> rangerDumpTask = TaskFactory.get(rangerDumpWork, conf);
      if (childTasks == null) {
        childTasks = new ArrayList<>();
      }
      childTasks.add(rangerDumpTask);
    } else {
      throw new SemanticException(ErrorMsg.REPL_INVALID_CONFIG_FOR_SERVICE.format("Authorizer "
        + conf.getVar(HiveConf.ConfVars.REPL_AUTHORIZATION_PROVIDER_SERVICE)
              + " not supported for replication ", ReplUtils.REPL_RANGER_SERVICE));
    }
  }

};