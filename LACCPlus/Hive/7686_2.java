//,temp,SQLStdHiveAccessController.java,619,644,temp,FallbackHiveAuthorizer.java,218,241
//,3
public class xxx {
  @Override
  public void applyAuthorizationConfigPolicy(HiveConf hiveConf) throws HiveAuthzPluginException {
    // from SQLStdHiveAccessController.applyAuthorizationConfigPolicy()
    if (sessionCtx.getClientType() == HiveAuthzSessionContext.CLIENT_TYPE.HIVESERVER2
            && hiveConf.getBoolVar(HiveConf.ConfVars.HIVE_AUTHORIZATION_ENABLED)) {

      // Configure PREEXECHOOKS with DisallowTransformHook to disallow transform queries
      String hooks = hiveConf.getVar(HiveConf.ConfVars.PREEXECHOOKS).trim();
      if (hooks.isEmpty()) {
        hooks = DisallowTransformHook.class.getName();
      } else {
        hooks = hooks + "," + DisallowTransformHook.class.getName();
      }
      LOG.debug("Configuring hooks : " + hooks);
      hiveConf.setVar(HiveConf.ConfVars.PREEXECHOOKS, hooks);

      SettableConfigUpdater.setHiveConfWhiteList(hiveConf);
      String curBlackList = hiveConf.getVar(HiveConf.ConfVars.HIVE_SERVER2_BUILTIN_UDF_BLACKLIST);
      if (curBlackList != null && curBlackList.trim().equals("reflect,reflect2,java_method")) {
        hiveConf.setVar(HiveConf.ConfVars.HIVE_SERVER2_BUILTIN_UDF_BLACKLIST, "reflect,reflect2,java_method,in_file");
      }

    }
  }

};