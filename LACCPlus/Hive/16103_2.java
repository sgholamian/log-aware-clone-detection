//,temp,ReplDumpTask.java,891,1031,temp,ReplDumpTask.java,542,720
//,3
public class xxx {
  private Long incrementalDump(Path dumpRoot, DumpMetaData dmd, Path cmRoot, Hive hiveDb) throws Exception {
    Long lastReplId;// get list of events matching dbPattern & tblPattern
    // go through each event, and dump out each event to a event-level dump dir inside dumproot
    String validTxnList = null;
    long waitUntilTime = 0;
    long bootDumpBeginReplId = -1;

    List<String> tableList = work.replScope.includeAllTables() ? null : new ArrayList<>();

    // If we are bootstrapping ACID tables, we need to perform steps similar to a regular
    // bootstrap (See bootstrapDump() for more details. Only difference here is instead of
    // waiting for the concurrent transactions to finish, we start dumping the incremental events
    // and wait only for the remaining time if any.
    if (needBootstrapAcidTablesDuringIncrementalDump()) {
      work.setBootstrap(true);
      bootDumpBeginReplId = queryState.getConf().getLong(ReplUtils.LAST_REPL_ID_KEY, -1L);
      assert (bootDumpBeginReplId >= 0);
      LOG.info("Dump for bootstrapping ACID tables during an incremental dump for db {}",
              work.dbNameOrPattern);
      long timeoutInMs = HiveConf.getTimeVar(conf,
              HiveConf.ConfVars.REPL_BOOTSTRAP_DUMP_OPEN_TXN_TIMEOUT, TimeUnit.MILLISECONDS);
      waitUntilTime = System.currentTimeMillis() + timeoutInMs;
    }
    // TODO : instead of simply restricting by message format, we should eventually
    // move to a jdbc-driver-stype registering of message format, and picking message
    // factory per event to decode. For now, however, since all messages have the
    // same factory, restricting by message format is effectively a guard against
    // older leftover data that would cause us problems.
    work.overrideLastEventToDump(hiveDb, bootDumpBeginReplId);
    IMetaStoreClient.NotificationFilter evFilter = new AndFilter(
        new ReplEventFilter(work.replScope),
        new CatalogFilter(MetaStoreUtils.getDefaultCatalog(conf)),
        new EventBoundaryFilter(work.eventFrom, work.eventTo));
    EventUtils.MSClientNotificationFetcher evFetcher
        = new EventUtils.MSClientNotificationFetcher(hiveDb);
    int maxEventLimit  = getMaxEventAllowed(work.maxEventLimit());
    EventUtils.NotificationEventIterator evIter = new EventUtils.NotificationEventIterator(
        evFetcher, work.eventFrom, maxEventLimit, evFilter);
    lastReplId = work.eventTo;
    Path ackFile = new Path(dumpRoot, ReplAck.EVENTS_DUMP.toString());
    long resumeFrom = Utils.fileExists(ackFile, conf) ? getResumeFrom(ackFile) : work.eventFrom;

    // Right now the only pattern allowed to be specified is *, which matches all the database
    // names. So passing dbname as is works since getDbNotificationEventsCount can exclude filter
    // on database name when it's *. In future, if we support more elaborate patterns, we will
    // have to pass DatabaseAndTableFilter created above to getDbNotificationEventsCount() to get
    // correct event count.
    String dbName = (null != work.dbNameOrPattern && !work.dbNameOrPattern.isEmpty())
        ? work.dbNameOrPattern
        : "?";
    Database db = hiveDb.getDatabase(dbName);
    if (db != null && !HiveConf.getBoolVar(conf, REPL_DUMP_METADATA_ONLY)) {
      setReplSourceFor(hiveDb, dbName, db);
    }

    long estimatedNumEvents = evFetcher.getDbNotificationEventsCount(work.eventFrom, dbName, work.eventTo,
        maxEventLimit);
    try {
      IncrementalDumpLogger replLogger =
          new IncrementalDumpLogger(dbName, dumpRoot.toString(), estimatedNumEvents, work.eventFrom, work.eventTo,
              maxEventLimit);
      work.setReplLogger(replLogger);
      replLogger.startLog();
      Map<String, Long> metricMap = new HashMap<>();
      metricMap.put(ReplUtils.MetricName.EVENTS.name(), estimatedNumEvents);
      work.getMetricCollector().reportStageStart(getName(), metricMap);
      long dumpedCount = resumeFrom - work.eventFrom;
      if (dumpedCount > 0) {
        LOG.info("Event id {} to {} are already dumped, skipping {} events", work.eventFrom, resumeFrom, dumpedCount);
      }
      cleanFailedEventDirIfExists(dumpRoot, resumeFrom);
      while (evIter.hasNext()) {
        NotificationEvent ev = evIter.next();
        lastReplId = ev.getEventId();
        if (ev.getEventId() <= resumeFrom) {
          continue;
        }

        //disable materialized-view replication if not configured
        if (!isMaterializedViewsReplEnabled()) {
          String tblName = ev.getTableName();
          if (tblName != null) {
            try {
              Table table = hiveDb.getTable(dbName, tblName);
              if (table != null && TableType.MATERIALIZED_VIEW.equals(table.getTableType())) {
                LOG.info("Attempt to dump materialized view : " + tblName);
                continue;
              }
            } catch (InvalidTableException te) {
              LOG.debug(te.getMessage());
            }
          }
        }

        Path evRoot = new Path(dumpRoot, String.valueOf(lastReplId));
        dumpEvent(ev, evRoot, dumpRoot, cmRoot, hiveDb);
        Utils.writeOutput(String.valueOf(lastReplId), ackFile, conf);
      }
      replLogger.endLog(lastReplId.toString());
      LOG.info("Done dumping events, preparing to return {},{}", dumpRoot.toUri(), lastReplId);
    } finally {
      //write the dmd always irrespective of success/failure to enable checkpointing in table level replication
      long executionId = conf.getLong(Constants.SCHEDULED_QUERY_EXECUTIONID, 0L);
      dmd.setDump(DumpType.INCREMENTAL, work.eventFrom, lastReplId, cmRoot, executionId,
        previousReplScopeModified());
      // If repl policy is changed (oldReplScope is set), then pass the current replication policy,
      // so that REPL LOAD would drop the tables which are not included in current policy.
      dmd.setReplScope(work.replScope);
      dmd.write(true);
    }
    try (FileList managedTblList = createTableFileList(dumpRoot, EximUtil.FILE_LIST);
         FileList extTableFileList = createTableFileList(dumpRoot, EximUtil.FILE_LIST_EXTERNAL)) {
      // Examine all the tables if required.
      if (shouldExamineTablesToDump() || (tableList != null)) {
        // If required wait more for any transactions open at the time of starting the ACID bootstrap.
        if (needBootstrapAcidTablesDuringIncrementalDump()) {
          assert (waitUntilTime > 0);
          validTxnList = getValidTxnListForReplDump(hiveDb, waitUntilTime);
        }
      /* When same dump dir is resumed because of check-pointing, we need to clear the existing metadata.
      We need to rewrite the metadata as the write id list will be changed.
      We can't reuse the previous write id as it might be invalid due to compaction. */
        Path bootstrapRoot = new Path(dumpRoot, ReplUtils.INC_BOOTSTRAP_ROOT_DIR_NAME);
        Path metadataPath = new Path(bootstrapRoot, EximUtil.METADATA_PATH_NAME);
        FileSystem fs = FileSystem.get(metadataPath.toUri(), conf);
        try {
          fs.delete(metadataPath, true);
        } catch (FileNotFoundException e) {
          // no worries
        }
        Path dbRootMetadata = new Path(metadataPath, dbName);
        Path dbRootData = new Path(bootstrapRoot, EximUtil.DATA_PATH_NAME + File.separator + dbName);
        boolean dataCopyAtLoad = conf.getBoolVar(HiveConf.ConfVars.REPL_RUN_DATA_COPY_TASKS_ON_TARGET);
        ReplExternalTables externalTablesWriter = new ReplExternalTables(conf);
        boolean isSingleTaskForExternalDb =
            conf.getBoolVar(REPL_EXTERNAL_WAREHOUSE_SINGLE_COPY_TASK) && work.replScope.includeAllTables();
        ArrayList<String> singleCopyPaths = getNonTableLevelCopyPaths(db, isSingleTaskForExternalDb);
        boolean isExternalTablePresent = false;
        for(String matchedDbName : Utils.matchesDb(hiveDb, work.dbNameOrPattern)) {
          for (String tableName : Utils.matchesTbl(hiveDb, matchedDbName, work.replScope)) {
            try {
              Table table = hiveDb.getTable(matchedDbName, tableName);

              // Dump external table locations if required.
              if (TableType.EXTERNAL_TABLE.equals(table.getTableType())
                      && shouldDumpExternalTableLocation()) {
                externalTablesWriter.dataLocationDump(table, extTableFileList, singleCopyPaths,
                        !isSingleTaskForExternalDb, conf);
                isExternalTablePresent = true;
              }

              // Dump the table to be bootstrapped if required.
              if (shouldBootstrapDumpTable(table)) {
                HiveWrapper.Tuple<Table> tableTuple = new HiveWrapper(hiveDb, matchedDbName).table(table);
                dumpTable(matchedDbName, tableName, validTxnList, dbRootMetadata, dbRootData, bootDumpBeginReplId,
                        hiveDb, tableTuple, managedTblList, dataCopyAtLoad);
              }
              if (tableList != null && isTableSatifiesConfig(table)) {
                tableList.add(tableName);
              }
            } catch (InvalidTableException te) {
              // Repl dump shouldn't fail if the table is dropped/renamed while dumping it.
              // Just log a debug message and skip it.
              LOG.debug(te.getMessage());
            }
          }
          // if it is not a table level replication, add a single task for
          // the database default location and the paths configured.
          if (isExternalTablePresent && shouldDumpExternalTableLocation() && isSingleTaskForExternalDb) {
            externalTablesWriter.dumpNonTableLevelCopyPaths(singleCopyPaths, extTableFileList, conf);
          }
        }
        dumpTableListToDumpLocation(tableList, dumpRoot, dbName, conf);
      }
      setDataCopyIterators(extTableFileList, managedTblList);
      work.getMetricCollector().reportStageEnd(getName(), Status.SUCCESS, lastReplId);
      return lastReplId;
    }
  }

};