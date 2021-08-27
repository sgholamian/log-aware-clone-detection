//,temp,VectorReduceSinkCommonOperator.java,350,374,temp,MapOperator.java,582,597
//,3
public class xxx {
  private void doCollect(HiveKey keyWritable, BytesWritable valueWritable) throws IOException {
    // Since this is a terminal operator, update counters explicitly -
    // forward is not called
    if (null != out) {
      numRows++;
      if (LOG.isInfoEnabled()) {
        if (numRows == cntr) {
          cntr = logEveryNRows == 0 ? cntr * 10 : numRows + logEveryNRows;
          if (cntr < 0 || numRows < 0) {
            cntr = 0;
            numRows = 1;
          }
          LOG.info(toString() + ": records written - " + numRows);
        }
      }

      // BytesWritable valueBytesWritable = (BytesWritable) valueWritable;
      // LOG.info("VectorReduceSinkCommonOperator collect keyWritable " + keyWritable.getLength() + " " +
      //     VectorizedBatchUtil.displayBytes(keyWritable.getBytes(), 0, keyWritable.getLength()) +
      //     " valueWritable " + valueBytesWritable.getLength() +
      //     VectorizedBatchUtil.displayBytes(valueBytesWritable.getBytes(), 0, valueBytesWritable.getLength()));

      out.collect(keyWritable, valueWritable);
    }
  }

};