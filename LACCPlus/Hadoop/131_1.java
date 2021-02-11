//,temp,TestMRJobClient.java,480,501,temp,TestMRJobClient.java,289,307
//,3
public class xxx {
  protected void testSubmittedJobList(Configuration conf) throws Exception {
    Job job = runJobInBackGround(conf);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    String line;
    int counter = 0;
    // only submitted
    int exitCode =
        runTool(conf, createJobClient(), new String[] { "-list" }, out);
    assertEquals("Exit code", 0, exitCode);
    BufferedReader br =
        new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
          out.toByteArray())));
    counter = 0;
    while ((line = br.readLine()) != null) {
      LOG.info("line = " + line);
      if (line.contains(job.getJobID().toString())) {
        counter++;
      }
    }
    // all jobs submitted! no current
    assertEquals(1, counter);
  }

};