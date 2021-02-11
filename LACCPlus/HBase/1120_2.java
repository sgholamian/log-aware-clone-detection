//,temp,TestFSHLogProvider.java,265,335,temp,TestFSHLogProvider.java,184,251
//,3
public class xxx {
  @Test
  public void testLogCleaning() throws Exception {
    LOG.info(currentTest.getMethodName());
    TableDescriptor htd =
        TableDescriptorBuilder.newBuilder(TableName.valueOf(currentTest.getMethodName()))
            .setColumnFamily(ColumnFamilyDescriptorBuilder.of("row")).build();
    TableDescriptor htd2 =
        TableDescriptorBuilder.newBuilder(TableName.valueOf(currentTest.getMethodName() + "2"))
            .setColumnFamily(ColumnFamilyDescriptorBuilder.of("row")).build();
    NavigableMap<byte[], Integer> scopes1 = new TreeMap<>(Bytes.BYTES_COMPARATOR);
    for (byte[] fam : htd.getColumnFamilyNames()) {
      scopes1.put(fam, 0);
    }
    NavigableMap<byte[], Integer> scopes2 = new TreeMap<>(Bytes.BYTES_COMPARATOR);
    for (byte[] fam : htd2.getColumnFamilyNames()) {
      scopes2.put(fam, 0);
    }
    Configuration localConf = new Configuration(conf);
    localConf.set(WALFactory.WAL_PROVIDER, FSHLogProvider.class.getName());
    WALFactory wals = new WALFactory(localConf, currentTest.getMethodName());
    try {
      RegionInfo hri = RegionInfoBuilder.newBuilder(htd.getTableName()).build();
      RegionInfo hri2 = RegionInfoBuilder.newBuilder(htd2.getTableName()).build();
      // we want to mix edits from regions, so pick our own identifier.
      WAL log = wals.getWAL(null);

      // Add a single edit and make sure that rolling won't remove the file
      // Before HBASE-3198 it used to delete it
      addEdits(log, hri, htd, 1, scopes1);
      log.rollWriter();
      assertEquals(1, AbstractFSWALProvider.getNumRolledLogFiles(log));

      // See if there's anything wrong with more than 1 edit
      addEdits(log, hri, htd, 2, scopes1);
      log.rollWriter();
      assertEquals(2, FSHLogProvider.getNumRolledLogFiles(log));

      // Now mix edits from 2 regions, still no flushing
      addEdits(log, hri, htd, 1, scopes1);
      addEdits(log, hri2, htd2, 1, scopes2);
      addEdits(log, hri, htd, 1, scopes1);
      addEdits(log, hri2, htd2, 1, scopes2);
      log.rollWriter();
      assertEquals(3, AbstractFSWALProvider.getNumRolledLogFiles(log));

      // Flush the first region, we expect to see the first two files getting
      // archived. We need to append something or writer won't be rolled.
      addEdits(log, hri2, htd2, 1, scopes2);
      log.startCacheFlush(hri.getEncodedNameAsBytes(), htd.getColumnFamilyNames());
      log.completeCacheFlush(hri.getEncodedNameAsBytes());
      log.rollWriter();
      int count = AbstractFSWALProvider.getNumRolledLogFiles(log);
      assertEquals(2, count);

      // Flush the second region, which removes all the remaining output files
      // since the oldest was completely flushed and the two others only contain
      // flush information
      addEdits(log, hri2, htd2, 1, scopes2);
      log.startCacheFlush(hri2.getEncodedNameAsBytes(), htd2.getColumnFamilyNames());
      log.completeCacheFlush(hri2.getEncodedNameAsBytes());
      log.rollWriter();
      assertEquals(0, AbstractFSWALProvider.getNumRolledLogFiles(log));
    } finally {
      if (wals != null) {
        wals.close();
      }
    }
  }

};