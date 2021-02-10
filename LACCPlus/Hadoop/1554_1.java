//,temp,TestBadRecords.java,249,270,temp,TestBadRecords.java,221,243
//,3
public class xxx {
    public void reduce(LongWritable key, Iterator<Text> values,
        OutputCollector<LongWritable, Text> output, Reporter reporter)
        throws IOException {
      while(values.hasNext()) {
        Text value = values.next();
        LOG.debug("REDUCE key:" +key +"  value:" + value);
        if(REDUCER_BAD_RECORDS.get(0).equals(value.toString())) {
          LOG.warn("REDUCE Encountered BAD record");
          System.exit(-1);
        }
        else if(REDUCER_BAD_RECORDS.get(1).equals(value.toString())) {
          try {
            LOG.warn("REDUCE Encountered BAD record");
            Thread.sleep(15*60*1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        output.collect(key, value);
      }
      
    }

};