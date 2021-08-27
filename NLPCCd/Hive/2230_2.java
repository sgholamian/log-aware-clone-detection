//,temp,sample_4114.java,2,16,temp,sample_4117.java,2,16
//,2
public class xxx {
public void dummy_method(){
ExportCommand exportMdCmd = new ExportCommand(dbName,tableName,null, exportLocation, true, evid);
CommandProcessorResponse ret2 = driver.run(exportMdCmd.get().get(0));
assertEquals(ret2.getResponseCode() + ":" + ret2.getErrorMessage(), null, ret2.getException());
List<String> exportPaths = exportMdCmd.cleanupLocationsAfterEvent();
assertEquals(1,exportPaths.size());
String metadata = getMetadataContents(exportPaths.get(0));
LOG.info(metadata);
assertTrue(metadata + "did not match \"repl.scope\"=\"metadata\"",metadata.matches(".*\"repl.scope\":\"metadata\".*"));
assertTrue(metadata + "has \"repl.last.id\"",metadata.matches(".*\"repl.last.id\":.*"));
ImportCommand importMdCmd = new ImportCommand(dbName, importedTableName, null, exportLocation, true, evid);


log.info("about to run");
}

};