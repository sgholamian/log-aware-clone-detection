//,temp,TestMRJobClient.java,454,478,temp,TestMRJobClient.java,289,307
//,3
public class xxx {
  protected void testAllJobList(String jobId, Configuration conf)
      throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // bad options

    int exitCode = runTool(conf, createJobClient(), new String[] { "-list",
        "alldata" }, out);
    assertEquals("Exit code", -1, exitCode);
    exitCode = runTool(conf, createJobClient(),
        // all jobs
        new String[] { "-list", "all" }, out);
    assertEquals("Exit code", 0, exitCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(out.toByteArray())));
    String line;
    int counter = 0;
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      if (line.contains(jobId)) {
        counter++;
      }
    }
    assertEquals(1, counter);
    out.reset();
  }

};