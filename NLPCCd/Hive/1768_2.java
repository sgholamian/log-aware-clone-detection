//,temp,sample_1838.java,2,16,temp,sample_1827.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT * from " + dbName + ".unptned_empty", empty, driverMirror);
run("LOAD DATA LOCAL INPATH '" + unptn_locn + "' OVERWRITE INTO TABLE " + dbName + ".unptned", driver);
verifySetup("SELECT * from " + dbName + ".unptned", unptn_data, driver);
run("CREATE TABLE " + dbName + ".unptned_late LIKE " + dbName + ".unptned", driver);
run("INSERT INTO TABLE " + dbName + ".unptned_late SELECT * FROM " + dbName + ".unptned", driver);
verifySetup("SELECT * from " + dbName + ".unptned_late", unptn_data, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};