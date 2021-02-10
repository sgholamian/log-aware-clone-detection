//,temp,TestMRJobClient.java,411,433,temp,TestMRJobClient.java,386,407
//,3
public class xxx {
  private void testJobStatus(String jobId, Configuration conf) throws Exception {
    CLI jc = createJobClient();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // bad options
    int exitCode = runTool(conf, jc, new String[] { "-status" }, out);
    assertEquals("Exit code", -1, exitCode);

    exitCode = runTool(conf, jc, new String[] { "-status", jobId }, out);
    assertEquals("Exit code", 0, exitCode);
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(out.toByteArray())));

    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      if (!line.contains("Job state:")) {
        continue;
      }
      break;
    }
    assertNotNull(line);
    assertTrue(line.contains("SUCCEEDED"));
  }

};