//,temp,TestValueIterReset.java,553,572,temp,TestLocalModeWithNewApis.java,102,122
//,3
public class xxx {
  static String readOutput(Path outDir, Configuration conf) 
      throws IOException {
    FileSystem fs = outDir.getFileSystem(conf);
    StringBuffer result = new StringBuffer();

    Path[] fileList = FileUtil.stat2Paths(fs.listStatus(outDir,
           new Utils.OutputFileUtils.OutputFilesFilter()));
    for (Path outputFile : fileList) {
      LOG.info("Path" + ": "+ outputFile);
      BufferedReader file = 
        new BufferedReader(new InputStreamReader(fs.open(outputFile)));
      String line = file.readLine();
      while (line != null) {
        result.append(line);
        result.append("\n");
        line = file.readLine();
      }
      file.close();
    }
    return result.toString();
  }

};