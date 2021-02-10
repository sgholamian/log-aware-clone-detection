//,temp,TestSetFile.java,134,191,temp,TestFileSystem.java,442,489
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    int megaBytes = 10;
    int files = 100;
    boolean noRead = false;
    boolean noWrite = false;
    boolean noSeek = false;
    boolean fastCheck = false;
    long seed = new Random().nextLong();

    String usage = "Usage: TestFileSystem -files N -megaBytes M [-noread] [-nowrite] [-noseek] [-fastcheck]";
    
    if (args.length == 0) {
      System.err.println(usage);
      System.exit(-1);
    }
    for (int i = 0; i < args.length; i++) {       // parse command line
      if (args[i].equals("-files")) {
        files = Integer.parseInt(args[++i]);
      } else if (args[i].equals("-megaBytes")) {
        megaBytes = Integer.parseInt(args[++i]);
      } else if (args[i].equals("-noread")) {
        noRead = true;
      } else if (args[i].equals("-nowrite")) {
        noWrite = true;
      } else if (args[i].equals("-noseek")) {
        noSeek = true;
      } else if (args[i].equals("-fastcheck")) {
        fastCheck = true;
      }
    }

    LOG.info("seed = "+seed);
    LOG.info("files = " + files);
    LOG.info("megaBytes = " + megaBytes);
  
    FileSystem fs = FileSystem.get(conf);

    if (!noWrite) {
      createControlFile(fs, megaBytes*MEGA, files, seed);
      writeTest(fs, fastCheck);
    }
    if (!noRead) {
      readTest(fs, fastCheck);
    }
    if (!noSeek) {
      seekTest(fs, fastCheck);
    }
  }

};