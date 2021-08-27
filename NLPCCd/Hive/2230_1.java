//,temp,sample_4114.java,2,16,temp,sample_4117.java,2,16
//,2
public class xxx {
public void dummy_method(){
ExportCommand exportCmd = new ExportCommand(dbName,tableName,null, exportLocation, false, evid);
CommandProcessorResponse ret2 = driver.run(exportCmd.get().get(0));
assertEquals(ret2.getResponseCode() + ":" + ret2.getErrorMessage(), null, ret2.getException());
List<String> exportPaths = exportCmd.cleanupLocationsAfterEvent();
assertEquals(1,exportPaths.size());
String metadata = getMetadataContents(exportPaths.get(0));
LOG.info(metadata);
assertTrue(metadata + "did not match \"repl.scope\"=\"all\"", metadata.matches(".*\"repl.scope\":\"all\".*"));
assertTrue(metadata + "has \"repl.last.id\"",metadata.matches(".*\"repl.last.id\":.*"));
ImportCommand importCmd = new ImportCommand(dbName, importedTableName, null, exportLocation, false, evid);


log.info("about to run");
}

};