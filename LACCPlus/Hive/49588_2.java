//,temp,MmMajorQueryCompactor.java,43,66,temp,MmMinorQueryCompactor.java,43,65
//,3
public class xxx {
  @Override void runCompaction(HiveConf hiveConf, Table table, Partition partition,
      StorageDescriptor storageDescriptor, ValidWriteIdList writeIds, CompactionInfo compactionInfo,
      AcidDirectory dir) throws IOException {
    LOG.debug(
        "Going to delete directories for aborted transactions for MM table " + table.getDbName()
            + "." + table.getTableName());
    QueryCompactor.Util.removeFilesForMmTable(hiveConf, dir);

    HiveConf driverConf = setUpDriverSession(hiveConf);

    String tmpPrefix = table.getDbName() + ".tmp_minor_compactor_" + table.getTableName() + "_";
    String tmpTableName = tmpPrefix + System.currentTimeMillis();
    String resultTmpTableName = tmpTableName + "_result";
    Path resultDeltaDir = QueryCompactor.Util.getCompactionResultDir(storageDescriptor, writeIds, driverConf,
        false, false, false, dir);

    List<String> createTableQueries = getCreateQueries(tmpTableName, table, storageDescriptor, dir,
        writeIds, resultDeltaDir);
    List<String> compactionQueries = getCompactionQueries(tmpTableName, resultTmpTableName, table);
    List<String> dropQueries = getDropQueries(tmpTableName);
    runCompactionQueries(driverConf, tmpTableName, storageDescriptor, writeIds, compactionInfo,
        Lists.newArrayList(resultDeltaDir), createTableQueries, compactionQueries, dropQueries);
  }

};