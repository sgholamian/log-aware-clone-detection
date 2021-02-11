//,temp,TestSetFile.java,134,191,temp,TestArrayFile.java,159,211
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    int count = 1024 * 1024;
    boolean create = true;
    boolean check = true;
    String file = FILE;
    String compress = "NONE";

    String usage = "Usage: TestSetFile [-count N] [-nocreate] [-nocheck] [-compress type] file";
      
    if (args.length == 0) {
      System.err.println(usage);
      System.exit(-1);
    }
      
    int i = 0;
    Path fpath=null;
    FileSystem fs = null;    
    try {
      for (; i < args.length; i++) {       // parse command line
        if (args[i] == null) {
          continue;
        } else if (args[i].equals("-count")) {
          count = Integer.parseInt(args[++i]);
        } else if (args[i].equals("-nocreate")) {
          create = false;
        } else if (args[i].equals("-nocheck")) {
          check = false;
        } else if (args[i].equals("-compress")) {
          compress = args[++i];
        } else {
          // file is required parameter
          file = args[i];
          fpath=new Path(file);
        }
      }
      
      fs = fpath.getFileSystem(conf);
      
      LOG.info("count = " + count);
      LOG.info("create = " + create);
      LOG.info("check = " + check);
      LOG.info("compress = " + compress);
      LOG.info("file = " + file);
      
      RandomDatum[] data = generate(count);
      
      if (create) {
        writeTest(fs, data, file, CompressionType.valueOf(compress));
      }
      
      if (check) {
        readTest(fs, data, file);
      }
  
    } finally {
      fs.close();
    }
  }

};