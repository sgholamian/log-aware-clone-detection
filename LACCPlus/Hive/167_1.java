//,temp,HiveIcebergMetaHook.java,406,422,temp,HiveIcebergMetaHook.java,384,404
//,3
public class xxx {
  @Override
  public void rollbackInsertTable(org.apache.hadoop.hive.metastore.api.Table table, boolean overwrite)
      throws MetaException {
    String tableName = TableIdentifier.of(table.getDbName(), table.getTableName()).toString();
    JobContext jobContext = getJobContextForCommitOrAbort(tableName, overwrite);
    OutputCommitter committer = new HiveIcebergOutputCommitter();
    try {
      LOG.info("rollbackInsertTable: Aborting job for jobID: {} and table: {}", jobContext.getJobID(), tableName);
      committer.abortJob(jobContext, JobStatus.State.FAILED);
    } catch (IOException e) {
      LOG.error("Error while trying to abort failed job. There might be uncleaned data files.", e);
      // no throwing here because the original commitInsertTable exception should be propagated
    } finally {
      // avoid config pollution with prefixed/suffixed keys
      cleanCommitConfig(tableName);
    }
  }

};