//,temp,TestMRJobClient.java,386,407,temp,TestMRJobClient.java,311,329
//,3
public class xxx {
  private void testListAttemptIds(String jobId, Configuration conf)
      throws Exception {
    CLI jc = createJobClient();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int exitCode = runTool(conf, jc, new String[] { "-list-attempt-ids" }, out);
    assertEquals("Exit code", -1, exitCode);
    exitCode = runTool(conf, jc, new String[] { "-list-attempt-ids", jobId,
        "MAP", "completed" }, out);
    assertEquals("Exit code", 0, exitCode);
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(out.toByteArray())));
    int counter = 0;
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      counter++;
    }
    assertEquals(1, counter);
  }

};