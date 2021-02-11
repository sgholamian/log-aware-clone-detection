//,temp,TestSequenceFileInputFilter.java,124,149,temp,TestSequenceFileInputFilter.java,101,122
//,3
public class xxx {
  public void testRegexFilter() throws Exception {
    // set the filter class
    LOG.info("Testing Regex Filter with patter: \\A10*");
    SequenceFileInputFilter.setFilterClass(job, 
                                           SequenceFileInputFilter.RegexFilter.class);
    SequenceFileInputFilter.RegexFilter.setPattern(job, "\\A10*");
    
    // clean input dir
    fs.delete(inDir, true);
  
    // for a variety of lengths
    for (int length = 1; length < MAX_LENGTH;
         length+= random.nextInt(MAX_LENGTH/10)+1) {
      LOG.info("******Number of records: "+length);
      createSequenceFile(length);
      int count = countRecords(0);
      assertEquals(count, length==0?0:(int)Math.log10(length)+1);
    }
    
    // clean up
    fs.delete(inDir, true);
  }

};