//,temp,TestCommands.java,447,515,temp,TestCommands.java,375,445
//,3
public class xxx {
  @Test
  public void testMetadataReplEximCommands() throws IOException, CommandProcessorException {
    // repl metadata export, has repl.last.id and repl.scope=metadata
    // import repl metadata dump, table metadata changed, allows override, has repl.last.id
    int evid = 222;
    String exportLocation = TEST_PATH + File.separator + "testMetadataReplExim";
    Path tempPath = new Path(TEST_PATH ,"testMetadataReplEximTmp");
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

    ExportCommand exportMdCmd = new ExportCommand(dbName,tableName,null,
        exportLocation, true, evid);

    LOG.info("About to run :" + exportMdCmd.get().get(0));
    driver.run(exportMdCmd.get().get(0));

    List<String> exportPaths = exportMdCmd.cleanupLocationsAfterEvent();
    assertEquals(1,exportPaths.size());
    String metadata = getMetadataContents(exportPaths.get(0));
    LOG.info("Export returned the following _metadata contents:");
    LOG.info(metadata);
    assertTrue(metadata + "did not match \"repl.scope\"=\"metadata\"",metadata.matches(".*\"repl.scope\":\"metadata\".*"));
    assertTrue(metadata + "has \"repl.last.id\"",metadata.matches(".*\"repl.last.id\":.*"));

    ImportCommand importMdCmd = new ImportCommand(dbName, importedTableName, null, exportLocation, true, evid);

    LOG.info("About to run :" + importMdCmd.get().get(0));
    driver.run(importMdCmd.get().get(0));

    driver.run("SELECT * from " + dbName + "." + importedTableName);

    List<String> values2 = new ArrayList<String>();
    driver.getResults(values2);

    assertEquals(0, values2.size());

    HCatTable importedTable = client.getTable(dbName,importedTableName);
    assertNotNull(importedTable);

    assertTrue(importedTable.getTblProps().containsKey("repl.last.id"));
  }

};