//,temp,TestFileSystem.java,442,489,temp,Logalyzer.java,255,327
//,3
public class xxx {
  public static void main(String[] args) {
    
    Log LOG = LogFactory.getLog(Logalyzer.class);
    
    String version = "Logalyzer.0.0.1";
    String usage = "Usage: Logalyzer [-archive -logs <urlsFile>] " +
      "-archiveDir <archiveDirectory> " +
      "-grep <pattern> -sort <column1,column2,...> -separator <separator> " +
      "-analysis <outputDirectory>";
    
    System.out.println(version);
    if (args.length == 0) {
      System.err.println(usage);
      System.exit(-1);
    }
    
    //Command line arguments
    boolean archive = false;
    boolean grep = false;
    boolean sort = false;
    
    String archiveDir = "";
    String logListURI = "";
    String grepPattern = ".*";
    String sortColumns = "";
    String columnSeparator = " ";
    String outputDirectory = "";
    
    for (int i = 0; i < args.length; i++) { // parse command line
      if (args[i].equals("-archive")) {
        archive = true;
      } else if (args[i].equals("-archiveDir")) {
        archiveDir = args[++i];
      } else if (args[i].equals("-grep")) {
        grep = true;
        grepPattern = args[++i];
      } else if (args[i].equals("-logs")) {
        logListURI = args[++i];
      } else if (args[i].equals("-sort")) {
        sort = true;
        sortColumns = args[++i];
      } else if (args[i].equals("-separator")) {
        columnSeparator = args[++i];
      } else if (args[i].equals("-analysis")) {
        outputDirectory = args[++i];
      }
    }
    
    LOG.info("analysisDir = " + outputDirectory);
    LOG.info("archiveDir = " + archiveDir);
    LOG.info("logListURI = " + logListURI);
    LOG.info("grepPattern = " + grepPattern);
    LOG.info("sortColumns = " + sortColumns);
    LOG.info("separator = " + columnSeparator);
    
    try {
      Logalyzer logalyzer = new Logalyzer();
      
      // Archive?
      if (archive) {
        logalyzer.doArchive(logListURI, archiveDir);
      }
      
      // Analyze?
      if (grep || sort) {
        logalyzer.doAnalyze(archiveDir, outputDirectory, grepPattern, sortColumns, columnSeparator);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      System.exit(-1);
    }
    
  } //main

};