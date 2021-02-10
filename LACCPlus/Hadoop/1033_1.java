//,temp,TestMRJobClient.java,386,407,temp,TestMRJobClient.java,333,350
//,3
public class xxx {
  private void testJobEvents(String jobId, Configuration conf) throws Exception {
    CLI jc = createJobClient();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int exitCode = runTool(conf, jc, new String[] { "-events" }, out);
    assertEquals("Exit code", -1, exitCode);

    exitCode = runTool(conf, jc, new String[] { "-events", jobId, "0", "100" },
        out);
    assertEquals("Exit code", 0, exitCode);
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(out.toByteArray())));
    int counter = 0;
    String attemptId = ("attempt" + jobId.substring(3));
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      if (line.contains(attemptId)) {
        counter++;
      }
    }
    assertEquals(2, counter);
  }

};