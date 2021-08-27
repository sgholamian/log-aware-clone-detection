//,temp,LaunchMapper.java,120,140,temp,LaunchMapper.java,106,119
//,3
public class xxx {
  private static void handleHadoopClasspathExtras(Configuration conf, Map<String, String> env)
    throws IOException {
    if(!TempletonUtils.isset(conf.get(JobSubmissionConstants.HADOOP_CLASSPATH_EXTRAS))) {
      return;
    }
    LOG.debug(HADOOP_CLASSPATH_EXTRAS + "=" + conf.get(HADOOP_CLASSPATH_EXTRAS));
    String[] files = conf.getStrings(HADOOP_CLASSPATH_EXTRAS);
    StringBuilder paths = new StringBuilder();
    FileSystem fs = FileSystem.getLocal(conf);//these have been localized already
    for(String f : files) {
      Path p = new Path(f);
      FileStatus fileStatus = fs.getFileStatus(p);
      paths.append(f);
      if(fileStatus.isDir()) {
        paths.append(File.separator).append("*");
      }
      paths.append(File.pathSeparator);
    }
    paths.setLength(paths.length() - 1);
    prependPathToVariable(HADOOP_CLASSPATH, env, paths.toString());
  }

};