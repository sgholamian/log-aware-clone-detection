//,temp,TestHCatClient.java,661,690,temp,TestHCatClient.java,638,659
//,3
public class xxx {
  @Test
  public void testPartitionSchema() throws Exception {
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

      client.createTable(HCatCreateTableDesc.create(dbName, tableName, columnSchema).partCols(partitionSchema).build());

      HCatTable table = client.getTable(dbName, tableName);
      List<HCatFieldSchema> partitionColumns = table.getPartCols();

      assertArrayEquals("Didn't get expected partition-schema back from the HCatTable.",
          partitionSchema.toArray(), partitionColumns.toArray());
      client.dropDatabase(dbName, false, HCatClient.DropDBMode.CASCADE);
    }
    catch (Exception unexpected) {
      LOG.error("Unexpected exception!", unexpected);
      assertTrue("Unexpected exception! " + unexpected.getMessage(), false);
    }
  }

};