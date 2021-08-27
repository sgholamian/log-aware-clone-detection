//,temp,LoadDatabase.java,112,120,temp,LoadDatabase.java,102,110
//,2
public class xxx {
  String getDbLocation(Database dbInMetadata) {
    if (context.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_RETAIN_CUSTOM_LOCATIONS_FOR_DB_ON_TARGET)
            && Boolean.parseBoolean(dbInMetadata.getParameters().get(ReplUtils.REPL_IS_CUSTOM_DB_LOC))) {
      String locOnTarget = new Path(dbInMetadata.getLocationUri()).toUri().getPath().toString();
      LOG.info("Using the custom location {} on the target", locOnTarget);
      return locOnTarget;
    }
    return null;
  }

};