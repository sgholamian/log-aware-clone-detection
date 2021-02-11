//,temp,TestMRSequenceFileInputFilter.java,141,167,temp,TestMRSequenceFileInputFilter.java,117,139
//,3
public class xxx {
  public void testPercentFilter() throws Exception {
    LOG.info("Testing Percent Filter with frequency: 1000");
    // set the filter class
    SequenceFileInputFilter.setFilterClass(job, 
      SequenceFileInputFilter.PercentFilter.class);
    SequenceFileInputFilter.PercentFilter.setFrequency(
      job.getConfiguration(), 1000);
      
    // clean input dir
    fs.delete(inDir, true);
    
    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length += random.nextInt(MAX_LENGTH / 10) + 1) {
      LOG.info("******Number of records: "+length);
      createSequenceFile(length);
      int count = countRecords(1);
      LOG.info("Accepted " + count + " records");
      int expectedCount = length / 1000;
      if (expectedCount * 1000 != length)
        expectedCount++;
      assertEquals(count, expectedCount);
    }
      
    // clean up
    fs.delete(inDir, true);
  }

};