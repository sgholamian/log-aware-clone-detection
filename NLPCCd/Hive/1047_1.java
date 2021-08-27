//,temp,sample_4115.java,2,16,temp,sample_4116.java,2,16
//,3
public class xxx {
public void dummy_method(){
CommandProcessorResponse ret = driver.run( "LOAD DATA LOCAL INPATH '"+tempLocation+"' OVERWRITE INTO TABLE "+ dbName+ "." + tableName );
assertEquals(ret.getResponseCode() + ":" + ret.getErrorMessage(), null, ret.getException());
CommandProcessorResponse selectRet = driver.run("SELECT * from " + dbName + "." + tableName);
assertEquals(selectRet.getResponseCode() + ":" + selectRet.getErrorMessage(), null, selectRet.getException());
List<String> values = new ArrayList<String>();
driver.getResults(values);
assertEquals(2, values.size());
assertEquals(data[0],values.get(0));
assertEquals(data[1],values.get(1));
ExportCommand exportMdCmd = new ExportCommand(dbName,tableName,null, exportLocation, true, evid);


log.info("about to run");
}

};