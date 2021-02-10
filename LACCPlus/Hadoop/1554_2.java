//,temp,TestBadRecords.java,249,270,temp,TestBadRecords.java,221,243
//,3
public class xxx {
    public void map(LongWritable key, Text val,
        OutputCollector<LongWritable, Text> output, Reporter reporter)
        throws IOException {
      String str = val.toString();
      LOG.debug("MAP key:" +key +"  value:" + str);
      if(MAPPER_BAD_RECORDS.get(0).equals(str)) {
        LOG.warn("MAP Encountered BAD record");
        System.exit(-1);
      }
      else if(MAPPER_BAD_RECORDS.get(1).equals(str)) {
        LOG.warn("MAP Encountered BAD record");
        throw new RuntimeException("Bad record "+str);
      }
      else if(MAPPER_BAD_RECORDS.get(2).equals(str)) {
        try {
          LOG.warn("MAP Encountered BAD record");
          Thread.sleep(15*60*1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      output.collect(key, val);
    }

};