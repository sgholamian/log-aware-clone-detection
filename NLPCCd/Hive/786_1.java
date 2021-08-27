//,temp,sample_1850.java,2,16,temp,sample_1868.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + insertDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);
replDumpId = incrementalDumpId;
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.unptned ORDER BY a", unptn_data, driverMirror);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};