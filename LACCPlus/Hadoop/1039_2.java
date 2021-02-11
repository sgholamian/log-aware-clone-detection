//,temp,TestMRJobClient.java,454,478,temp,TestMRJobClient.java,289,307
//,3
public class xxx {
  private void testListBlackList(Configuration conf) throws Exception {
    CLI jc = createJobClient();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int exitCode = runTool(conf, jc, new String[] {
        "-list-blacklisted-trackers", "second in" }, out);
    assertEquals("Exit code", -1, exitCode);
    exitCode = runTool(conf, jc, new String[] { "-list-blacklisted-trackers" },
        out);
    assertEquals("Exit code", 0, exitCode);
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(out.toByteArray())));
    int counter = 0;
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      counter++;
    }
    assertEquals(0, counter);
  }

};