//,temp,TestDynamicInputFormat.java,68,76,temp,TestCopyMapper.java,81,89
//,3
public class xxx {
  private static Configuration getConfigurationForCluster() throws IOException {
    Configuration configuration = new Configuration();
    System.setProperty("test.build.data", "target/tmp/build/TEST_COPY_MAPPER/data");
    configuration.set("hadoop.log.dir", "target/tmp");
    configuration.set("dfs.namenode.fs-limits.min-block-size", "0");
    LOG.debug("fs.default.name  == " + configuration.get("fs.default.name"));
    LOG.debug("dfs.http.address == " + configuration.get("dfs.http.address"));
    return configuration;
  }

};