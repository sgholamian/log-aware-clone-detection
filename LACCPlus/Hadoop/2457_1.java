//,temp,TestValueIterReset.java,553,572,temp,TestLocalModeWithNewApis.java,102,122
//,3
public class xxx {
  private void validateOutput() throws IOException {
    Path[] outputFiles = FileUtil.stat2Paths(
        localFs.listStatus(new Path(TEST_ROOT_DIR + "/out"),
            new Utils.OutputFileUtils.OutputFilesFilter()));
    if (outputFiles.length > 0) {
      InputStream is = localFs.open(outputFiles[0]);
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      while (line != null) {
        StringTokenizer tokeniz = new StringTokenizer(line, "\t");
        String key = tokeniz.nextToken();
        String value = tokeniz.nextToken();
        LOG.info("Output: key: "+ key + " value: "+ value);
        int errors = Integer.parseInt(value);
        assertTrue(errors == 0);
        line = reader.readLine();
      }   
      reader.close();
    }
  }

};