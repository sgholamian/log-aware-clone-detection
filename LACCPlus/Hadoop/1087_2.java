//,temp,TestSetFile.java,134,191,temp,DistributedFSCheck.java,233,278
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    int testType = TEST_TYPE_READ;
    int bufferSize = DEFAULT_BUFFER_SIZE;
    String resFileName = DEFAULT_RES_FILE_NAME;
    String rootName = "/";
    boolean viewStats = false;

    String usage = "Usage: DistributedFSCheck [-root name] [-clean] [-resFile resultFileName] [-bufferSize Bytes] [-stats] ";
    
    if (args.length == 1 && args[0].startsWith("-h")) {
      System.err.println(usage);
      System.exit(-1);
    }
    for(int i = 0; i < args.length; i++) {       // parse command line
      if (args[i].equals("-root")) {
        rootName = args[++i];
      } else if (args[i].startsWith("-clean")) {
        testType = TEST_TYPE_CLEANUP;
      } else if (args[i].equals("-bufferSize")) {
        bufferSize = Integer.parseInt(args[++i]);
      } else if (args[i].equals("-resFile")) {
        resFileName = args[++i];
      } else if (args[i].startsWith("-stat")) {
        viewStats = true;
      }
    }

    LOG.info("root = " + rootName);
    LOG.info("bufferSize = " + bufferSize);
  
    Configuration conf = new Configuration();  
    conf.setInt("test.io.file.buffer.size", bufferSize);
    DistributedFSCheck test = new DistributedFSCheck(conf);

    if (testType == TEST_TYPE_CLEANUP) {
      test.cleanup();
      return;
    }
    test.createInputFile(rootName);
    long tStart = System.currentTimeMillis();
    test.runDistributedFSCheck();
    long execTime = System.currentTimeMillis() - tStart;
    
    test.analyzeResult(execTime, resFileName, viewStats);
    // test.cleanup();  // clean up after all to restore the system state
  }

};