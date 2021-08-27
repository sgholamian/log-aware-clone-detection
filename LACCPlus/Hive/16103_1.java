//,temp,ReplDumpTask.java,891,1031,temp,ReplDumpTask.java,542,720
//,3
public class xxx {
  Long bootStrapDump(Path dumpRoot, DumpMetaData dmd, Path cmRoot, Hive hiveDb)
          throws Exception {
    // bootstrap case
    // Last repl id would've been captured during compile phase in queryState configs before opening txn.
    // This is needed as we dump data on ACID/MM tables based on read snapshot or else we may lose data from
    // concurrent txns when bootstrap dump in progress. If it is not available, then get it from metastore.
    Long bootDumpBeginReplId = queryState.getConf().getLong(ReplUtils.LAST_REPL_ID_KEY, -1L);
    assert (bootDumpBeginReplId >= 0L);
    List<String> tableList;

    LOG.info("Bootstrap Dump for db {}", work.dbNameOrPattern);
    long timeoutInMs = HiveConf.getTimeVar(conf,
            HiveConf.ConfVars.REPL_BOOTSTRAP_DUMP_OPEN_TXN_TIMEOUT, TimeUnit.MILLISECONDS);
    long waitUntilTime = System.currentTimeMillis() + timeoutInMs;
    String validTxnList = getValidTxnListForReplDump(hiveDb, waitUntilTime);
    Path metadataPath = new Path(dumpRoot, EximUtil.METADATA_PATH_NAME);
    if (shouldResumePreviousDump(dmd)) {
      //clear the metadata. We need to rewrite the metadata as the write id list will be changed
      //We can't reuse the previous write id as it might be invalid due to compaction
      metadataPath.getFileSystem(conf).delete(metadataPath, true);
    }
    List<EximUtil.DataCopyPath> functionsBinaryCopyPaths = Collections.emptyList();
    try (FileList managedTblList = createTableFileList(dumpRoot, EximUtil.FILE_LIST);
         FileList extTableFileList = createTableFileList(dumpRoot, EximUtil.FILE_LIST_EXTERNAL)) {
      for (String dbName : Utils.matchesDb(hiveDb, work.dbNameOrPattern)) {
        LOG.debug("Dumping db: " + dbName);
        // TODO : Currently we don't support separate table list for each database.
        tableList = work.replScope.includeAllTables() ? null : new ArrayList<>();
        Database db = hiveDb.getDatabase(dbName);
        if ((db != null) && (ReplUtils.isFirstIncPending(db.getParameters()))) {
          // For replicated (target) database, until after first successful incremental load, the database will not be
          // in a consistent state. Avoid allowing replicating this database to a new target.
          throw new HiveException("Replication dump not allowed for replicated database" +
                  " with first incremental dump pending : " + dbName);
        }

        if (db != null && !HiveConf.getBoolVar(conf, REPL_DUMP_METADATA_ONLY)) {
          setReplSourceFor(hiveDb, dbName, db);
        }

        int estimatedNumTables = Utils.getAllTables(hiveDb, dbName, work.replScope).size();
        int estimatedNumFunctions = hiveDb.getFunctions(dbName, "*").size();
        BootstrapDumpLogger replLogger =
            new BootstrapDumpLogger(dbName, dumpRoot.toString(), estimatedNumTables, estimatedNumFunctions);
        work.setReplLogger(replLogger);
        replLogger.startLog();
        Map<String, Long> metricMap = new HashMap<>();
        metricMap.put(ReplUtils.MetricName.TABLES.name(), (long) estimatedNumTables);
        metricMap.put(ReplUtils.MetricName.FUNCTIONS.name(), (long) estimatedNumFunctions);
        work.getMetricCollector().reportStageStart(getName(), metricMap);
        Path dbRoot = dumpDbMetadata(dbName, metadataPath, bootDumpBeginReplId, hiveDb);
        Path dbDataRoot = new Path(new Path(dumpRoot, EximUtil.DATA_PATH_NAME), dbName);
        boolean dataCopyAtLoad = conf.getBoolVar(HiveConf.ConfVars.REPL_RUN_DATA_COPY_TASKS_ON_TARGET);
        functionsBinaryCopyPaths = dumpFunctionMetadata(dbName, dbRoot, dbDataRoot, hiveDb, dataCopyAtLoad);

        String uniqueKey = Utils.setDbBootstrapDumpState(hiveDb, dbName);
        Exception caught = null;
        try {
          ReplExternalTables externalTablesWriter = new ReplExternalTables(conf);
          boolean isSingleTaskForExternalDb =
              conf.getBoolVar(REPL_EXTERNAL_WAREHOUSE_SINGLE_COPY_TASK) && work.replScope.includeAllTables();
          ArrayList<String> singleCopyPaths = getNonTableLevelCopyPaths(db, isSingleTaskForExternalDb);
          boolean isExternalTablePresent = false;
          for (String tblName : Utils.matchesTbl(hiveDb, dbName, work.replScope)) {
            Table table = null;
            try {
              HiveWrapper.Tuple<Table> tableTuple = new HiveWrapper(hiveDb, dbName).table(tblName, conf);
              table = tableTuple != null ? tableTuple.object : null;

              //disable materialized-view replication if not configured
              if(tableTuple != null && !isMaterializedViewsReplEnabled()
                      && TableType.MATERIALIZED_VIEW.equals(tableTuple.object.getTableType())){
                LOG.info("Attempt to dump materialized view : " + tblName);
                continue;
              }

              LOG.debug("Dumping table: " + tblName + " to db root " + dbRoot.toUri());
              if (shouldDumpExternalTableLocation()
                      && TableType.EXTERNAL_TABLE.equals(tableTuple.object.getTableType())) {
                LOG.debug("Adding table {} to external tables list", tblName);
                externalTablesWriter.dataLocationDump(tableTuple.object, extTableFileList,
                    singleCopyPaths, !isSingleTaskForExternalDb, conf);
                isExternalTablePresent = true;
              }
              dumpTable(dbName, tblName, validTxnList, dbRoot, dbDataRoot,
                      bootDumpBeginReplId,
                      hiveDb, tableTuple, managedTblList, dataCopyAtLoad);
            } catch (InvalidTableException te) {
              // Bootstrap dump shouldn't fail if the table is dropped/renamed while dumping it.
              // Just log a debug message and skip it.
              LOG.debug(te.getMessage());
            }
            dumpConstraintMetadata(dbName, tblName, dbRoot, hiveDb);
            if (tableList != null && isTableSatifiesConfig(table)) {
              tableList.add(tblName);
            }
          }

          // if it is not a table level replication, add a single task for
          // the database default location for external tables.
          if (isExternalTablePresent && shouldDumpExternalTableLocation()
              && isSingleTaskForExternalDb) {
            externalTablesWriter.dumpNonTableLevelCopyPaths(singleCopyPaths, extTableFileList, conf);
          }
          dumpTableListToDumpLocation(tableList, dumpRoot, dbName, conf);
        } catch (Exception e) {
          caught = e;
        } finally {
          try {
            Utils.resetDbBootstrapDumpState(hiveDb, dbName, uniqueKey);
          } catch (Exception e) {
            if (caught == null) {
              throw e;
            } else {
              LOG.error("failed to reset the db state for " + uniqueKey
                      + " on failure of repl dump", e);
              throw caught;
            }
          }
          if (caught != null) {
            throw caught;
          }
        }
        replLogger.endLog(bootDumpBeginReplId.toString());
        work.getMetricCollector().reportStageEnd(getName(), Status.SUCCESS, bootDumpBeginReplId);
      }
      work.setFunctionCopyPathIterator(functionsBinaryCopyPaths.iterator());
      setDataCopyIterators(extTableFileList, managedTblList);
      LOG.info("Preparing to return {},{}->{}",
        dumpRoot.toUri(), bootDumpBeginReplId, currentNotificationId(hiveDb));
      return bootDumpBeginReplId;
    } finally {
      //write the dmd always irrespective of success/failure to enable checkpointing in table level replication
      Long bootDumpEndReplId = currentNotificationId(hiveDb);
      long executorId = conf.getLong(Constants.SCHEDULED_QUERY_EXECUTIONID, 0L);
      dmd.setDump(DumpType.BOOTSTRAP, bootDumpBeginReplId, bootDumpEndReplId, cmRoot, executorId,
        previousReplScopeModified());
      dmd.setReplScope(work.replScope);
      dmd.write(true);
    }
  }

};