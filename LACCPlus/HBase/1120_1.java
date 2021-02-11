//,temp,TestFSHLogProvider.java,265,335,temp,TestFSHLogProvider.java,184,251
//,3
public class xxx {
  @Test
  public void testWALArchiving() throws IOException {
    LOG.debug(currentTest.getMethodName());
    TableDescriptor table1 =
        TableDescriptorBuilder.newBuilder(TableName.valueOf(currentTest.getMethodName() + "1"))
            .setColumnFamily(ColumnFamilyDescriptorBuilder.of("row")).build();
    TableDescriptor table2 =
        TableDescriptorBuilder.newBuilder(TableName.valueOf(currentTest.getMethodName() + "2"))
            .setColumnFamily(ColumnFamilyDescriptorBuilder.of("row")).build();
    NavigableMap<byte[], Integer> scopes1 = new TreeMap<>(Bytes.BYTES_COMPARATOR);
    for (byte[] fam : table1.getColumnFamilyNames()) {
      scopes1.put(fam, 0);
    }
    NavigableMap<byte[], Integer> scopes2 = new TreeMap<>(Bytes.BYTES_COMPARATOR);
    for (byte[] fam : table2.getColumnFamilyNames()) {
      scopes2.put(fam, 0);
    }
    Configuration localConf = new Configuration(conf);
    localConf.set(WALFactory.WAL_PROVIDER, FSHLogProvider.class.getName());
    WALFactory wals = new WALFactory(localConf, currentTest.getMethodName());
    try {
      WAL wal = wals.getWAL(null);
      assertEquals(0, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      RegionInfo hri1 = RegionInfoBuilder.newBuilder(table1.getTableName()).build();
      RegionInfo hri2 = RegionInfoBuilder.newBuilder(table2.getTableName()).build();
      // variables to mock region sequenceIds.
      // start with the testing logic: insert a waledit, and roll writer
      addEdits(wal, hri1, table1, 1, scopes1);
      wal.rollWriter();
      // assert that the wal is rolled
      assertEquals(1, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // add edits in the second wal file, and roll writer.
      addEdits(wal, hri1, table1, 1, scopes1);
      wal.rollWriter();
      // assert that the wal is rolled
      assertEquals(2, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // add a waledit to table1, and flush the region.
      addEdits(wal, hri1, table1, 3, scopes1);
      flushRegion(wal, hri1.getEncodedNameAsBytes(), table1.getColumnFamilyNames());
      // roll log; all old logs should be archived.
      wal.rollWriter();
      assertEquals(0, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // add an edit to table2, and roll writer
      addEdits(wal, hri2, table2, 1, scopes2);
      wal.rollWriter();
      assertEquals(1, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // add edits for table1, and roll writer
      addEdits(wal, hri1, table1, 2, scopes1);
      wal.rollWriter();
      assertEquals(2, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // add edits for table2, and flush hri1.
      addEdits(wal, hri2, table2, 2, scopes2);
      flushRegion(wal, hri1.getEncodedNameAsBytes(), table2.getColumnFamilyNames());
      // the log : region-sequenceId map is
      // log1: region2 (unflushed)
      // log2: region1 (flushed)
      // log3: region2 (unflushed)
      // roll the writer; log2 should be archived.
      wal.rollWriter();
      assertEquals(2, AbstractFSWALProvider.getNumRolledLogFiles(wal));
      // flush region2, and all logs should be archived.
      addEdits(wal, hri2, table2, 2, scopes2);
      flushRegion(wal, hri2.getEncodedNameAsBytes(), table2.getColumnFamilyNames());
      wal.rollWriter();
      assertEquals(0, AbstractFSWALProvider.getNumRolledLogFiles(wal));
    } finally {
      if (wals != null) {
        wals.close();
      }
    }
  }

};