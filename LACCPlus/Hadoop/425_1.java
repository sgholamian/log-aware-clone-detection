//,temp,TestMRJobClient.java,503,520,temp,TestMRJobClient.java,411,433
//,3
public class xxx {
  protected void verifyJobPriority(String jobId, String priority,
      Configuration conf, CLI jc) throws Exception {
    PipedInputStream pis = new PipedInputStream();
    PipedOutputStream pos = new PipedOutputStream(pis);
    int exitCode = runTool(conf, jc, new String[] { "-list", "all" }, pos);
    assertEquals("Exit code", 0, exitCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(pis));
    String line;
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      if (!line.contains(jobId)) {
        continue;
      }
      assertTrue(line.contains(priority));
      break;
    }
    pis.close();
  }

};