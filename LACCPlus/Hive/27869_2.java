//,temp,TestCommands.java,447,515,temp,TestCommands.java,375,445
//,3
public class xxx {
  @Test
  public void testBasicReplEximCommands() throws IOException, CommandProcessorException {
    // repl export, has repl.last.id and repl.scope=all in it
    // import repl dump, table has repl.last.id on it (will likely be 0)
    int evid = 111;
    String exportLocation = TEST_PATH + File.separator + "testBasicReplExim";
    Path tempPath = new Path(TEST_PATH ,"testBasicReplEximTmp");
    String tempLocation = tempPath.toUri().getPath();

    String dbName = "exim";
    String tableName = "basicSrc";
    String importedTableName = "basicDst";
    List<HCatFieldSchema> cols = HCatSchemaUtils.getHCatSchema("b:string").getFields();

    client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);
    client.createDatabase(HCatCreateDBDesc.create(dbName).ifNotExists(false).build());

    HCatTable table = (new HCatTable(dbName, tableName)).cols(cols).fileFormat("textfile");

    client.createTable(HCatCreateTableDesc.create(table).build());
    HCatTable t = client.getTable(dbName, tableName);
    assertNotNull(t);

    String[] data = new String[]{ "eleven" , "twelve" };

    HcatTestUtils.createTestDataFile(tempLocation,data);

    driver.run("LOAD DATA LOCAL INPATH '"+tempLocation+"' OVERWRITE INTO TABLE "+ dbName+ "." + tableName);

    driver.run("SELECT * from " + dbName + "." + tableName);

    List<String> values = new ArrayList<String>();
    driver.getResults(values);

    assertEquals(2, values.size());
    assertEquals(data[0],values.get(0));
    assertEquals(data[1],values.get(1));

    ExportCommand exportCmd = new ExportCommand(dbName,tableName,null,
        exportLocation, false, evid);

    LOG.info("About to run :" + exportCmd.get().get(0));
    driver.run(exportCmd.get().get(0));

    List<String> exportPaths = exportCmd.cleanupLocationsAfterEvent();
    assertEquals(1,exportPaths.size());
    String metadata = getMetadataContents(exportPaths.get(0));
    LOG.info("Export returned the following _metadata contents:");
    LOG.info(metadata);
    assertTrue(metadata + "did not match \"repl.scope\"=\"all\"", metadata.matches(".*\"repl.scope\":\"all\".*"));
    assertTrue(metadata + "has \"repl.last.id\"",metadata.matches(".*\"repl.last.id\":.*"));

    ImportCommand importCmd = new ImportCommand(dbName, importedTableName, null, exportLocation, false, evid);

    LOG.info("About to run :" + importCmd.get().get(0));
    driver.run(importCmd.get().get(0));

    driver.run("SELECT * from " + dbName + "." + importedTableName);

    List<String> values2 = new ArrayList<String>();
    driver.getResults(values2);

    assertEquals(2, values2.size());
    assertEquals(data[0],values2.get(0));
    assertEquals(data[1],values2.get(1));

    HCatTable importedTable = client.getTable(dbName,importedTableName);
    assertNotNull(importedTable);

    assertTrue(importedTable.getTblProps().containsKey("repl.last.id"));
  }

};