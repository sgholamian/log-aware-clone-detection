//,temp,HiveIcebergMetaHook.java,406,422,temp,HiveIcebergMetaHook.java,384,404
//,3
public class xxx {
  @Override
  public void commitInsertTable(org.apache.hadoop.hive.metastore.api.Table table, boolean overwrite)
      throws MetaException {
    String tableName = TableIdentifier.of(table.getDbName(), table.getTableName()).toString();
    JobContext jobContext = getJobContextForCommitOrAbort(tableName, overwrite);
    boolean failure = false;
    try {
      OutputCommitter committer = new HiveIcebergOutputCommitter();
      committer.commitJob(jobContext);
    } catch (Exception e) {
      failure = true;
      LOG.error("Error while trying to commit job", e);
      throw new MetaException(StringUtils.stringifyException(e));
    } finally {
      // if there's a failure, the configs will still be needed in rollbackInsertTable
      if (!failure) {
        // avoid config pollution with prefixed/suffixed keys
        cleanCommitConfig(tableName);
      }
    }
  }

};