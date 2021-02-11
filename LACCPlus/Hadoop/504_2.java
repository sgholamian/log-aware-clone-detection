//,temp,TestValueIterReset.java,309,414,temp,TestValueIterReset.java,179,299
//,3
public class xxx {
  private static int test1(IntWritable key, 
                           MarkableIterator<IntWritable> values)
  throws IOException {

    IntWritable i;
    int errors = 0;
    int count = 0;
    
    ArrayList<IntWritable> expectedValues = new ArrayList<IntWritable>();
    ArrayList<IntWritable> expectedValues1 = new ArrayList<IntWritable>();

    LOG.info("Executing TEST:1 for Key:" + key);

    values.mark();
    LOG.info("TEST:1. Marking");

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);
      expectedValues.add(i);
      if (count == 2) {
        break;
      }
      count ++;
    }

    values.reset();
    LOG.info("TEST:1. Reset");
    count = 0;

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);

      if (count < expectedValues.size()) {
        if (i != expectedValues.get(count)) {
          errors ++;
          LOG.info("TEST:1. Check:1 Expected: " + expectedValues.get(count) +
              ", Got: " + i);
          return errors;
        }
      }
      
      // We have moved passed the first mark, but still in the memory cache
      if (count == 3) {
        values.mark();
        LOG.info("TEST:1. Marking -- " + key + ": " + i);
      }

      if (count >= 3) {
        expectedValues1.add(i);
      }
      
      if (count == 5) {
        break;
      }
      count ++;
    }

    if (count < expectedValues.size()) {
      LOG.info(("TEST:1 Check:2. Iterator returned lesser values"));
      errors ++;
      return errors;
    }
    
    values.reset();
    count = 0;
    LOG.info("TEST:1. Reset");
    expectedValues.clear();

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);

      if (count < expectedValues1.size()) {
        if (i != expectedValues1.get(count)) {
          errors ++;
          LOG.info("TEST:1. Check:3 Expected: " + expectedValues1.get(count)
              + ", Got: " + i);
          return errors;
        }
      }
      
      // We have moved passed the previous mark, but now we are in the file
      // cache
      if (count == 25) {
        values.mark();
        LOG.info("TEST:1. Marking -- " + key + ":" + i);
      }
      
      if (count >= 25) {
        expectedValues.add(i);
      }
      count ++;
    }

    if (count < expectedValues1.size()) {
      LOG.info(("TEST:1 Check:4. Iterator returned fewer values"));
      errors ++;
      return errors;
    }

    values.reset();
    LOG.info("TEST:1. Reset");
    count = 0;

    while (values.hasNext()) {
      i = values.next();
      LOG.info(key + ":" + i);

      if (i != expectedValues.get(count)) {
        errors ++;
        LOG.info("TEST:1. Check:5 Expected: " + expectedValues.get(count)
            + ", Got: " + i);
        return errors;
      }
    }

    LOG.info("TEST:1 Done");
    return errors;
  }

};