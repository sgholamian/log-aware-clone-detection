//,temp,LaunchMapper.java,120,140,temp,LaunchMapper.java,106,119
//,3
public class xxx {
  private static void handleSqoop(Configuration conf, Map<String, String> env) throws IOException {
    if(TempletonUtils.isset(conf.get(Sqoop.LIB_JARS))) {
      //LIB_JARS should only be set if Sqoop is auto-shipped
      LOG.debug(Sqoop.LIB_JARS + "=" + conf.get(Sqoop.LIB_JARS));
      String[] files = conf.getStrings(Sqoop.LIB_JARS);
      StringBuilder jdbcJars = new StringBuilder();
      for(String f : files) {
        jdbcJars.append(f).append(File.pathSeparator);
      }
      jdbcJars.setLength(jdbcJars.length() - 1);
      //this makes the jars available to Sqoop client
      prependPathToVariable(HADOOP_CLASSPATH, env, jdbcJars.toString());
    }
  }

};