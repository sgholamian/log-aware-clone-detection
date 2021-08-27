//,temp,SparkReduceRecordHandler.java,598,646,temp,ExecReducer.java,249,281
//,3
public class xxx {
  @Override
  public void close() {
    super.close();
    // No row was processed
    if (!anyRow) {
      LOG.trace("Close called without any rows processed");
    }
    try {
      if (vectorized) {
        if (batch.size > 0) {

          if (handleGroupKey) {
            // Indicate last batch of current group.
            reducer.setNextVectorBatchGroupStatus(/* isLastGroupBatch */ true);
          }

          forwardBatch(/* resetValueColumnsOnly */ false);
        }
      } else {
        if (groupKey != null) {
          // If a operator wants to do some work at the end of a group
          LOG.trace("End Group");
          reducer.endGroup();
        }
      }

      reducer.close(abort);

      if (localWork != null) {
        for (Operator<? extends OperatorDesc> dummyOp : localWork.getDummyParentOp()) {
          dummyOp.close(abort);
        }
      }

      ReportStats rps = new ReportStats(rp, jc);
      reducer.preorderMap(rps);

    } catch (Exception e) {
      if (!abort) {
        // signal new failure to map-reduce
        LOG.error("Hit error while closing operators - failing tree");
        throw new RuntimeException("Hive Runtime Error while closing operators: "
          + e.getMessage(), e);
      }
    } finally {
      MapredContext.close();
      Utilities.clearWorkMap(jc);
    }
  }

};