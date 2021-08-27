//,temp,AddPartitionHandler.java,48,122,temp,CreateTableHandler.java,40,93
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} ADD_PARTITION message : {}", fromEventId(), eventMessageAsJSON);

    // We do not dump partitions during metadata only bootstrap dump (See TableExport
    // .getPartitions(), for bootstrap dump we pass tableSpec with TABLE_ONLY set.). So don't
    // dump partition related events for metadata-only dump.
    if (withinContext.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_DUMP_METADATA_ONLY)) {
      return;
    }

    AddPartitionMessage apm = (AddPartitionMessage) eventMessage;
    org.apache.hadoop.hive.metastore.api.Table tobj = apm.getTableObj();
    if (tobj == null) {
      LOG.debug("Event#{} was a ADD_PTN_EVENT with no table listed", fromEventId());
      return;
    }

    final Table qlMdTable = new Table(tobj);
    if (!Utils.shouldReplicate(withinContext.replicationSpec, qlMdTable, true,
            withinContext.getTablesForBootstrap(), withinContext.oldReplScope,  withinContext.hiveConf)) {
      return;
    }

    Iterable<org.apache.hadoop.hive.metastore.api.Partition> ptns = apm.getPartitionObjs();
    if ((ptns == null) || (!ptns.iterator().hasNext())) {
      LOG.debug("Event#{} was an ADD_PTN_EVENT with no partitions", fromEventId());
      return;
    }

    Iterable<Partition> qlPtns = StreamSupport.stream(ptns.spliterator(), true).map(
        input -> {
          if (input == null) {
            return null;
          }
          try {
            return new Partition(qlMdTable, input);
          } catch (HiveException e) {
            throw new IllegalArgumentException(e);
          }
        }).collect(Collectors.toList());

    Path metaDataPath = new Path(withinContext.eventRoot, EximUtil.METADATA_NAME);
    EximUtil.createExportDump(
        metaDataPath.getFileSystem(withinContext.hiveConf),
        metaDataPath,
        qlMdTable,
        qlPtns,
        withinContext.replicationSpec,
        withinContext.hiveConf);

    boolean copyAtLoad = withinContext.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_RUN_DATA_COPY_TASKS_ON_TARGET);
    Iterator<PartitionFiles> partitionFilesIter = apm.getPartitionFilesIter().iterator();

    // We expect one to one mapping between partitions and file iterators. For external table, this
    // list would be empty. So, it is enough to check hasNext outside the loop.
    if (partitionFilesIter.hasNext()) {
      for (Partition qlPtn : qlPtns) {
        Iterable<String> files = partitionFilesIter.next().getFiles();
        if (files != null) {
          if (copyAtLoad) {
            // encoded filename/checksum of files, write into _files
            Path ptnDataPath = new Path(withinContext.eventRoot, EximUtil.DATA_PATH_NAME + File.separator
                    + qlPtn.getName());
            writeEncodedDumpFiles(withinContext, files, ptnDataPath);
          } else {
            for (String file : files) {
              writeFileEntry(qlMdTable, qlPtn, file, withinContext);
            }
          }
        }
      }
    }
    withinContext.createDmd(this).write();
  }

};