//,temp,TestBadRecords.java,110,198,temp,TestStreamingBadRecords.java,94,155
//,3
public class xxx {
  private void validateOutput(JobConf conf, RunningJob runningJob, 
      List<String> mapperBadRecords, List<String> redBadRecords) 
    throws Exception{
    LOG.info(runningJob.getCounters().toString());
    assertTrue(runningJob.isSuccessful());
    
    //validate counters
    Counters counters = runningJob.getCounters();
    assertEquals(counters.findCounter(TaskCounter.MAP_SKIPPED_RECORDS).
        getCounter(),mapperBadRecords.size());
    
    int mapRecs = input.size() - mapperBadRecords.size();
    assertEquals(counters.findCounter(TaskCounter.MAP_INPUT_RECORDS).
        getCounter(),mapRecs);
    assertEquals(counters.findCounter(TaskCounter.MAP_OUTPUT_RECORDS).
        getCounter(),mapRecs);
    
    int redRecs = mapRecs - redBadRecords.size();
    assertEquals(counters.findCounter(TaskCounter.REDUCE_SKIPPED_RECORDS).
        getCounter(),redBadRecords.size());
    assertEquals(counters.findCounter(TaskCounter.REDUCE_SKIPPED_GROUPS).
        getCounter(),redBadRecords.size());
    assertEquals(counters.findCounter(TaskCounter.REDUCE_INPUT_GROUPS).
        getCounter(),redRecs);
    assertEquals(counters.findCounter(TaskCounter.REDUCE_INPUT_RECORDS).
        getCounter(),redRecs);
    assertEquals(counters.findCounter(TaskCounter.REDUCE_OUTPUT_RECORDS).
        getCounter(),redRecs);
    
    //validate skipped records
    Path skipDir = SkipBadRecords.getSkipOutputPath(conf);
    assertNotNull(skipDir);
    Path[] skips = FileUtil.stat2Paths(getFileSystem().listStatus(skipDir));
    List<String> mapSkipped = new ArrayList<String>();
    List<String> redSkipped = new ArrayList<String>();
    for(Path skipPath : skips) {
      LOG.info("skipPath: " + skipPath);
      
      SequenceFile.Reader reader = new SequenceFile.Reader(
          getFileSystem(), skipPath, conf);
      Object key = ReflectionUtils.newInstance(reader.getKeyClass(), conf);
      Object value = ReflectionUtils.newInstance(reader.getValueClass(), 
          conf);
      key = reader.next(key);
      while(key!=null) {
        value = reader.getCurrentValue(value);
        LOG.debug("key:"+key+" value:"+value.toString());
        if(skipPath.getName().contains("_r_")) {
          redSkipped.add(value.toString());
        } else {
          mapSkipped.add(value.toString());
        }
        key = reader.next(key);
      }
      reader.close();
    }
    assertTrue(mapSkipped.containsAll(mapperBadRecords));
    assertTrue(redSkipped.containsAll(redBadRecords));
    
    Path[] outputFiles = FileUtil.stat2Paths(
        getFileSystem().listStatus(getOutputDir(),
        new Utils.OutputFileUtils.OutputFilesFilter()));
    
    List<String> mapperOutput=getProcessed(input, mapperBadRecords);
    LOG.debug("mapperOutput " + mapperOutput.size());
    List<String> reducerOutput=getProcessed(mapperOutput, redBadRecords);
    LOG.debug("reducerOutput " + reducerOutput.size());
    
   if (outputFiles.length > 0) {
      InputStream is = getFileSystem().open(outputFiles[0]);
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = reader.readLine();
      int counter = 0;
      while (line != null) {
        counter++;
        StringTokenizer tokeniz = new StringTokenizer(line, "\t");
        String key = tokeniz.nextToken();
        String value = tokeniz.nextToken();
        LOG.debug("Output: key:"+key + "  value:"+value);
        assertTrue(value.contains("hello"));
        
        
        assertTrue(reducerOutput.contains(value));
        line = reader.readLine();
      }
      reader.close();
      assertEquals(reducerOutput.size(), counter);
    }
  }

};