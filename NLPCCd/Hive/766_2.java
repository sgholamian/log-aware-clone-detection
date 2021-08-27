//,temp,sample_1854.java,2,16,temp,sample_1891.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL DUMP " + dbName + " FROM " + replDumpId + " LIMIT " + numOfEventsTrunc3, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);
replDumpId = incrementalDumpId;
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.unptned ORDER BY a", empty, driverMirror);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};