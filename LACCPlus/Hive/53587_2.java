//,temp,AddPartitionHandler.java,48,122,temp,CreateTableHandler.java,40,93
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} CREATE_TABLE message : {}", fromEventId(), eventMessageAsJSON);
    org.apache.hadoop.hive.metastore.api.Table tobj = eventMessage.getTableObj();

    if (tobj == null) {
      LOG.debug("Event#{} was a CREATE_TABLE_EVENT with no table listed", fromEventId());
      return;
    }

    Table qlMdTable = new Table(tobj);

    if (!Utils.shouldReplicate(withinContext.replicationSpec, qlMdTable, true,
            withinContext.getTablesForBootstrap(), withinContext.oldReplScope, withinContext.hiveConf)) {
      return;
    }

    if (qlMdTable.isView()) {
      withinContext.replicationSpec.setIsMetadataOnly(true);
    }

    // If we are not dumping data about a table, we shouldn't be dumping basic statistics
    // as well, since that won't be accurate. So reset them to what they would look like for an
    // empty table.
    if (Utils.shouldDumpMetaDataOnly(withinContext.hiveConf)
            || Utils.shouldDumpMetaDataOnlyForExternalTables(qlMdTable, withinContext.hiveConf)) {
      qlMdTable.setStatsStateLikeNewTable();
    }

    Path metaDataPath = new Path(withinContext.eventRoot, EximUtil.METADATA_NAME);
    EximUtil.createExportDump(
        metaDataPath.getFileSystem(withinContext.hiveConf),
        metaDataPath,
        qlMdTable,
        null,
        withinContext.replicationSpec,
        withinContext.hiveConf);

    boolean copyAtLoad = withinContext.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_RUN_DATA_COPY_TASKS_ON_TARGET);
    Iterable<String> files = eventMessage.getFiles();
    if (files != null) {
      if (copyAtLoad) {
        // encoded filename/checksum of files, write into _files
        Path dataPath = new Path(withinContext.eventRoot, EximUtil.DATA_PATH_NAME);
        writeEncodedDumpFiles(withinContext, files, dataPath);
      } else {
        for (String file : files) {
          writeFileEntry(qlMdTable, null, file, withinContext);
        }
      }
    }

    withinContext.createDmd(this).write();
  }

};