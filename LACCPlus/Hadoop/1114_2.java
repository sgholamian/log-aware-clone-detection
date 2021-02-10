//,temp,TestBadRecords.java,110,198,temp,TestStreamingBadRecords.java,94,155
//,3
public class xxx {
  private void validateOutput(RunningJob runningJob, boolean validateCount) 
    throws Exception {
    LOG.info(runningJob.getCounters().toString());
    assertTrue(runningJob.isSuccessful());
    
    if(validateCount) {
     //validate counters
      String counterGrp = "org.apache.hadoop.mapred.Task$Counter";
      Counters counters = runningJob.getCounters();
      assertEquals(counters.findCounter(counterGrp, "MAP_SKIPPED_RECORDS").
          getCounter(),MAPPER_BAD_RECORDS.size());
      
      int mapRecs = INPUTSIZE - MAPPER_BAD_RECORDS.size();
      assertEquals(counters.findCounter(counterGrp, "MAP_INPUT_RECORDS").
          getCounter(),mapRecs);
      assertEquals(counters.findCounter(counterGrp, "MAP_OUTPUT_RECORDS").
          getCounter(),mapRecs);
      
      int redRecs = mapRecs - REDUCER_BAD_RECORDS.size();
      assertEquals(counters.findCounter(counterGrp, "REDUCE_SKIPPED_RECORDS").
          getCounter(),REDUCER_BAD_RECORDS.size());
      assertEquals(counters.findCounter(counterGrp, "REDUCE_SKIPPED_GROUPS").
          getCounter(),REDUCER_BAD_RECORDS.size());
      assertEquals(counters.findCounter(counterGrp, "REDUCE_INPUT_GROUPS").
          getCounter(),redRecs);
      assertEquals(counters.findCounter(counterGrp, "REDUCE_INPUT_RECORDS").
          getCounter(),redRecs);
      assertEquals(counters.findCounter(counterGrp, "REDUCE_OUTPUT_RECORDS").
          getCounter(),redRecs);
    }
    
    List<String> badRecs = new ArrayList<String>();
    badRecs.addAll(MAPPER_BAD_RECORDS);
    badRecs.addAll(REDUCER_BAD_RECORDS);
    Path[] outputFiles = FileUtil.stat2Paths(
        getFileSystem().listStatus(getOutputDir(),
        new Utils.OutputFileUtils.OutputFilesFilter()));
    
    if (outputFiles.length > 0) {
      InputStream is = getFileSystem().open(outputFiles[0]);
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      int counter = 0;
      while (line != null) {
        counter++;
        StringTokenizer tokeniz = new StringTokenizer(line, "\t");
        String value = tokeniz.nextToken();
        int index = value.indexOf("hey");
        assertTrue(index>-1);
        if(index>-1) {
          String heyStr = value.substring(index);
          assertTrue(!badRecs.contains(heyStr));
        }
        
        line = reader.readLine();
      }
      reader.close();
      if(validateCount) {
        assertEquals(INPUTSIZE-badRecs.size(), counter);
      }
    }
  }

};