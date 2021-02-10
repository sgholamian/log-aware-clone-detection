//,temp,TestJobCleanup.java,216,259,temp,TestJobCleanup.java,157,181
//,3
public class xxx {
  private void testKilledJob(String fileName,
      Class<? extends OutputCommitter> committer, String[] exclude)
      throws IOException {
    JobConf jc = mr.createJobConf();
    Path outDir = getNewOutputDir();
    configureJob(jc, "kill job with abort()", 1, 0, outDir);
    // set the job to wait for long
    jc.setMapperClass(UtilsForTests.KillMapper.class);
    jc.setOutputCommitter(committer);

    JobClient jobClient = new JobClient(jc);
    RunningJob job = jobClient.submitJob(jc);
    JobID id = job.getID();

    Counters counters = job.getCounters();

    // wait for the map to be launched
    while (true) {
      if (counters.getCounter(JobCounter.TOTAL_LAUNCHED_MAPS) == 1) {
        break;
      }
      LOG.info("Waiting for a map task to be launched");
      UtilsForTests.waitFor(100);
      counters = job.getCounters();
    }

    job.killJob(); // kill the job

    job.waitForCompletion(); // wait for the job to complete
    assertEquals("Job was not killed", JobStatus.KILLED, job.getJobState());

    if (fileName != null) {
      Path testFile = new Path(outDir, fileName);
      assertTrue("File " + testFile + " missing for job " + id,
          fileSys.exists(testFile));
    }

    // check if the files from the missing set exists
    for (String ex : exclude) {
      Path file = new Path(outDir, ex);
      assertFalse("File " + file + " should not be present for killed job "
          + id, fileSys.exists(file));
    }
  }

};