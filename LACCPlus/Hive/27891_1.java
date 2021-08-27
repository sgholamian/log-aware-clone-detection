//,temp,TestHCatClient.java,750,811,temp,TestHCatClient.java,692,748
//,3
public class xxx {
  @Test
  public void testDropPartitionsWithPartialSpec() throws Exception {
    try {
      HCatClient client = HCatClient.create(new Configuration(hcatConf));
      final String dbName = "myDb";
      final String tableName = "myTable";

      client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);

      client.createDatabase(HCatCreateDBDesc.create(dbName).build());
      List<HCatFieldSchema> columnSchema = Arrays.asList(new HCatFieldSchema("foo", Type.INT, ""),
          new HCatFieldSchema("bar", Type.STRING, ""));

      List<HCatFieldSchema> partitionSchema = Arrays.asList(new HCatFieldSchema("dt", Type.STRING, ""),
          new HCatFieldSchema("grid", Type.STRING, ""));

      HCatTable table = new HCatTable(dbName, tableName).cols(columnSchema).partCols(partitionSchema);
      client.createTable(HCatCreateTableDesc.create(table, false).build());

      // Verify that the table was created successfully.
      table = client.getTable(dbName, tableName);
      assertNotNull("Table couldn't be queried for. ", table);

      Map<String, String> partitionSpec = new HashMap<String, String>();
      partitionSpec.put("grid", "AB");
      partitionSpec.put("dt", "2011_12_31");
      client.addPartition(HCatAddPartitionDesc.create(new HCatPartition(table, partitionSpec,
          makePartLocation(table, partitionSpec))).build());
      partitionSpec.put("grid", "AB");
      partitionSpec.put("dt", "2012_01_01");
      client.addPartition(HCatAddPartitionDesc.create(new HCatPartition(table, partitionSpec,
          makePartLocation(table, partitionSpec))).build());
      partitionSpec.put("dt", "2012_01_01");
      partitionSpec.put("grid", "OB");
      client.addPartition(HCatAddPartitionDesc.create(new HCatPartition(table, partitionSpec,
          makePartLocation(table, partitionSpec))).build());
      partitionSpec.put("dt", "2012_01_01");
      partitionSpec.put("grid", "XB");
      client.addPartition(HCatAddPartitionDesc.create(new HCatPartition(table, partitionSpec,
          makePartLocation(table, partitionSpec))).build());

      Map<String, String> partialPartitionSpec = new HashMap<String, String>();
      partialPartitionSpec.put("dt", "2012_01_01");

      client.dropPartitions(dbName, tableName, partialPartitionSpec, true);

      List<HCatPartition> partitions = client.getPartitions(dbName, tableName);
      assertEquals("Unexpected number of partitions.", 1, partitions.size());
      assertArrayEquals("Mismatched partition.", new String[]{"2011_12_31", "AB"}, partitions.get(0).getValues().toArray());

      List<HCatFieldSchema> partColumns = partitions.get(0).getPartColumns();
      assertEquals(2, partColumns.size());
      assertEquals("dt", partColumns.get(0).getName());
      assertEquals("grid", partColumns.get(1).getName());

      client.dropDatabase(dbName, false, HCatClient.DropDBMode.CASCADE);
    }
    catch (Exception unexpected) {
      LOG.error("Unexpected exception!", unexpected);
      assertTrue("Unexpected exception! " + unexpected.getMessage(), false);
    }
  }

};