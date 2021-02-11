//,temp,TestSequenceFileInputFilter.java,151,170,temp,TestMRSequenceFileInputFilter.java,169,189
//,3
public class xxx {
  public void testMD5Filter() throws Exception {
    // set the filter class
    LOG.info("Testing MD5 Filter with frequency: 1000");
    SequenceFileInputFilter.setFilterClass(job, 
      SequenceFileInputFilter.MD5Filter.class);
    SequenceFileInputFilter.MD5Filter.setFrequency(
      job.getConfiguration(), 1000);
      
    // clean input dir
    fs.delete(inDir, true);
    
    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length += random.nextInt(MAX_LENGTH / 10) + 1) {
      LOG.info("******Number of records: " + length);
      createSequenceFile(length);
      LOG.info("Accepted " + countRecords(0) + " records");
    }
    // clean up
    fs.delete(inDir, true);
  }

};