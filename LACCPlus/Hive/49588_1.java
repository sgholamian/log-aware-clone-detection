//,temp,MmMajorQueryCompactor.java,43,66,temp,MmMinorQueryCompactor.java,43,65
//,3
public class xxx {
  @Override void runCompaction(HiveConf hiveConf, Table table, Partition partition, StorageDescriptor storageDescriptor,
      ValidWriteIdList writeIds, CompactionInfo compactionInfo, AcidDirectory dir) throws IOException {
    LOG.debug("Going to delete directories for aborted transactions for MM table " + table.getDbName() + "." + table
        .getTableName());
    QueryCompactor.Util.removeFilesForMmTable(hiveConf, dir);

    // Set up the session for driver.
    HiveConf driverConf = new HiveConf(hiveConf);
    driverConf.set(HiveConf.ConfVars.HIVE_QUOTEDID_SUPPORT.varname, "column");

    // Note: we could skip creating the table and just add table type stuff directly to the
    //       "insert overwrite directory" command if there were no bucketing or list bucketing.
    String tmpPrefix = table.getDbName() + ".tmp_compactor_" + table.getTableName() + "_";
    String tmpTableName = tmpPrefix + System.currentTimeMillis();
    Path resultBaseDir = QueryCompactor.Util.getCompactionResultDir(
        storageDescriptor, writeIds, driverConf, true, true, false, null);

    List<String> createTableQueries = getCreateQueries(tmpTableName, table, storageDescriptor,
        resultBaseDir.toString());
    List<String> compactionQueries = getCompactionQueries(table, partition, tmpTableName);
    List<String> dropQueries = getDropQueries(tmpTableName);
    runCompactionQueries(driverConf, tmpTableName, storageDescriptor, writeIds, compactionInfo,
        Lists.newArrayList(resultBaseDir), createTableQueries, compactionQueries, dropQueries);
  }

};