//,temp,sample_4115.java,2,16,temp,sample_4116.java,2,16
//,3
public class xxx {
public void dummy_method(){
driver.getResults(values);
assertEquals(2, values.size());
assertEquals(data[0],values.get(0));
assertEquals(data[1],values.get(1));
ExportCommand exportMdCmd = new ExportCommand(dbName,tableName,null, exportLocation, true, evid);
CommandProcessorResponse ret2 = driver.run(exportMdCmd.get().get(0));
assertEquals(ret2.getResponseCode() + ":" + ret2.getErrorMessage(), null, ret2.getException());
List<String> exportPaths = exportMdCmd.cleanupLocationsAfterEvent();
assertEquals(1,exportPaths.size());
String metadata = getMetadataContents(exportPaths.get(0));


log.info("export returned the following metadata contents");
}

};