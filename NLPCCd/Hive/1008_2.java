//,temp,sample_1893.java,2,16,temp,sample_1880.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL LOAD " + dbName + "_dupe FROM '" + replDumpLocn + "'", driverMirror);
String[] unptn_data = new String[] { "eleven", "twelve" };
String[] empty = new String[] {};
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[0] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[1] + "')", driver);
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};