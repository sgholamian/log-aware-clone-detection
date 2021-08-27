//,temp,sample_1842.java,2,16,temp,sample_1830.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[0] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[1] + "')", driver);
verifySetup("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data, driver);
run("CREATE TABLE " + dbName + ".unptned_late LIKE " + dbName + ".unptned", driver);
run("INSERT INTO TABLE " + dbName + ".unptned_late SELECT * FROM " + dbName + ".unptned", driver);
verifySetup("SELECT * from " + dbName + ".unptned_late ORDER BY a", unptn_data, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};