//,temp,TestMapProgress.java,90,92,temp,LocalJobRunner.java,705,709
//,3
public class xxx {
    @Override
    public void reportNextRecordRange(TaskAttemptID taskid, 
        SortedRanges.Range range) throws IOException {
      LOG.info("Task " + taskid + " reportedNextRecordRange " + range);
    }

};