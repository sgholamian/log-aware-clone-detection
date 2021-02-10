//,temp,TestJobCleanup.java,216,259,temp,TestJobCleanup.java,157,181
//,3
public class xxx {
  private void testSuccessfulJob(String filename,
      Class<? extends OutputCommitter> committer, String[] exclude)
      throws IOException {
    JobConf jc = mr.createJobConf();
    Path outDir = getNewOutputDir();
    configureJob(jc, "job with cleanup()", 1, 0, outDir);
    jc.setOutputCommitter(committer);

    JobClient jobClient = new JobClient(jc);
    RunningJob job = jobClient.submitJob(jc);
    JobID id = job.getID();
    job.waitForCompletion();

    LOG.info("Job finished : " + job.isComplete());
    Path testFile = new Path(outDir, filename);
    assertTrue("Done file \"" + testFile + "\" missing for job " + id,
        fileSys.exists(testFile));

    // check if the files from the missing set exists
    for (String ex : exclude) {
      Path file = new Path(outDir, ex);
      assertFalse("File " + file + " should not be present for successful job "
          + id, fileSys.exists(file));
    }
  }

};