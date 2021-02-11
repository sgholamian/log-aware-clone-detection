//,temp,TestValueIterReset.java,309,414,temp,TestValueIterReset.java,179,299
//,3
public class xxx {
  private static int test2(IntWritable key,
                           MarkableIterator<IntWritable> values)
  throws IOException {

    IntWritable i;
    int errors = 0;
    int count = 0;
    
    ArrayList<IntWritable> expectedValues = new ArrayList<IntWritable>();
    ArrayList<IntWritable> expectedValues1 = new ArrayList<IntWritable>();

    LOG.info("Executing TEST:2 for Key:" + key);

    values.mark();
    LOG.info("TEST:2 Marking");

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);
      expectedValues.add(i);
      if (count == 8) {
        break;
      }
      count ++;
    }

    values.reset();
    count = 0;
    LOG.info("TEST:2 reset");

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);
      
      if (count < expectedValues.size()) {
        if (i != expectedValues.get(count)) {
          errors ++;
          LOG.info("TEST:2. Check:1 Expected: " + expectedValues.get(count)
              + ", Got: " + i);
          return errors;
        }
      }

      // We have moved passed the first mark, but still reading from the
      // memory cache
      if (count == 3) {
        values.mark();
        LOG.info("TEST:2. Marking -- " + key + ":" + i);
      }
      
      if (count >= 3) {
        expectedValues1.add(i);
      }
      count ++;
    }

    values.reset();
    LOG.info("TEST:2. Reset");
    expectedValues.clear();
    count = 0;

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);

      if (count < expectedValues1.size()) {
        if (i != expectedValues1.get(count)) {
          errors ++;
          LOG.info("TEST:2. Check:2 Expected: " + expectedValues1.get(count)
              + ", Got: " + i);
          return errors;
        }
      }
      
      // We have moved passed the previous mark, but now we are in the file
      // cache
      if (count == 20) {
        values.mark();
        LOG.info("TEST:2. Marking -- " + key + ":" + i);
      }
      
      if (count >= 20) {
        expectedValues.add(i);
      }
      count ++;
    }

    values.reset();
    count = 0;
    LOG.info("TEST:2. Reset");

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);

      if (i != expectedValues.get(count)) {
        errors ++;
        LOG.info("TEST:2. Check:1 Expected: " + expectedValues.get(count)
            + ", Got: " + i);
        return errors;
      }
    }

    LOG.info("TEST:2 Done");
    return errors;
  }

};