//,temp,SQLStdHiveAccessController.java,619,644,temp,FallbackHiveAuthorizer.java,218,241
//,3
public class xxx {
  @Override
  public void applyAuthorizationConfigPolicy(HiveConf hiveConf) throws HiveAuthzPluginException {
    // First apply configuration applicable to both Hive Cli and HiveServer2
    // Not adding any authorization related restrictions to hive cli
    // grant all privileges for table to its owner - set this in cli as well so that owner
    // has permissions via HiveServer2 as well.
    hiveConf.setVar(ConfVars.HIVE_AUTHORIZATION_TABLE_OWNER_GRANTS, "INSERT,SELECT,UPDATE,DELETE");

    // Apply rest of the configuration only to HiveServer2
    if (sessionCtx.getClientType() == CLIENT_TYPE.HIVESERVER2
        && hiveConf.getBoolVar(ConfVars.HIVE_AUTHORIZATION_ENABLED)) {

      // Configure PREEXECHOOKS with DisallowTransformHook to disallow transform queries
      String hooks = hiveConf.getVar(ConfVars.PREEXECHOOKS).trim();
      if (hooks.isEmpty()) {
        hooks = DisallowTransformHook.class.getName();
      } else {
        hooks = hooks + "," + DisallowTransformHook.class.getName();
      }
      LOG.debug("Configuring hooks : " + hooks);
      hiveConf.setVar(ConfVars.PREEXECHOOKS, hooks);

      SettableConfigUpdater.setHiveConfWhiteList(hiveConf);

    }
  }

};