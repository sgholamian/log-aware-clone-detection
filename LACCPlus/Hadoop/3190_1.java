//,temp,TestDistCpViewFs.java,55,73,temp,TestIntegration.java,72,86
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws URISyntaxException{
    try {
      Path fswd = FileSystem.get(getConf()).getWorkingDirectory();
      Configuration vConf = ViewFileSystemTestSetup.createConfig(false); 
      ConfigUtil.addLink(vConf, "/usr", new URI(fswd.toString())); 
      fs = FileSystem.get(FsConstants.VIEWFS_URI, vConf);
      fs.setWorkingDirectory(new Path("/usr"));
      listFile = new Path("target/tmp/listing").makeQualified(fs.getUri(),
              fs.getWorkingDirectory());
      target = new Path("target/tmp/target").makeQualified(fs.getUri(),
              fs.getWorkingDirectory()); 
      root = new Path("target/tmp").makeQualified(fs.getUri(),
              fs.getWorkingDirectory()).toString();
      TestDistCpUtils.delete(fs, root);
    } catch (IOException e) {
      LOG.error("Exception encountered ", e);
    }
  }

};