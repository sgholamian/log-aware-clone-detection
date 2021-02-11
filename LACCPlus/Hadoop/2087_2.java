//,temp,TestDistributedShell.java,74,122,temp,TestUnmanagedAMLauncher.java,58,98
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws InterruptedException, IOException {
    LOG.info("Starting up YARN cluster");
    conf.setInt(YarnConfiguration.RM_SCHEDULER_MINIMUM_ALLOCATION_MB, 128);
    if (yarnCluster == null) {
      yarnCluster = new MiniYARNCluster(
          TestUnmanagedAMLauncher.class.getSimpleName(), 1, 1, 1);
      yarnCluster.init(conf);
      yarnCluster.start();
      //get the address
      Configuration yarnClusterConfig = yarnCluster.getConfig();
      LOG.info("MiniYARN ResourceManager published address: " +
               yarnClusterConfig.get(YarnConfiguration.RM_ADDRESS));
      LOG.info("MiniYARN ResourceManager published web address: " +
               yarnClusterConfig.get(YarnConfiguration.RM_WEBAPP_ADDRESS));
      String webapp = yarnClusterConfig.get(YarnConfiguration.RM_WEBAPP_ADDRESS);
      assertTrue("Web app address still unbound to a host at " + webapp,
        !webapp.startsWith("0.0.0.0"));
      LOG.info("Yarn webapp is at "+ webapp);
      URL url = Thread.currentThread().getContextClassLoader()
          .getResource("yarn-site.xml");
      if (url == null) {
        throw new RuntimeException(
            "Could not find 'yarn-site.xml' dummy file in classpath");
      }
      //write the document to a buffer (not directly to the file, as that
      //can cause the file being written to get read -which will then fail.
      ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
      yarnClusterConfig.writeXml(bytesOut);
      bytesOut.close();
      //write the bytes to the file in the classpath
      OutputStream os = new FileOutputStream(new File(url.getPath()));
      os.write(bytesOut.toByteArray());
      os.close();
    }
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      LOG.info("setup thread sleep interrupted. message=" + e.getMessage());
    }
  }

};