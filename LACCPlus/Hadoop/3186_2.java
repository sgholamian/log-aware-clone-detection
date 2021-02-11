//,temp,TestDFSIO.java,379,400,temp,DistributedFSCheck.java,190,215
//,3
public class xxx {
    void collectStats(OutputCollector<Text, Text> output, 
                      String name, 
                      long execTime, 
                      Object corruptedBlock) throws IOException {
      output.collect(new Text(AccumulatingReducer.VALUE_TYPE_LONG + "blocks"),
          new Text(String.valueOf(1)));

      if (corruptedBlock.getClass().getName().endsWith("String")) {
        output.collect(
            new Text(AccumulatingReducer.VALUE_TYPE_STRING + "badBlocks"),
            new Text((String)corruptedBlock));
        return;
      }
      long totalSize = ((Long)corruptedBlock).longValue();
      float ioRateMbSec = (float)totalSize * 1000 / (execTime * 0x100000);
      LOG.info("Number of bytes processed = " + totalSize);
      LOG.info("Exec time = " + execTime);
      LOG.info("IO rate = " + ioRateMbSec);
      
      output.collect(new Text(AccumulatingReducer.VALUE_TYPE_LONG + "size"),
          new Text(String.valueOf(totalSize)));
      output.collect(new Text(AccumulatingReducer.VALUE_TYPE_LONG + "time"),
          new Text(String.valueOf(execTime)));
      output.collect(new Text(AccumulatingReducer.VALUE_TYPE_FLOAT + "rate"),
          new Text(String.valueOf(ioRateMbSec*1000)));
    }

};