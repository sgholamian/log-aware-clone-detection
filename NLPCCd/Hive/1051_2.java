//,temp,sample_1867.java,2,16,temp,sample_1881.java,2,16
//,3
public class xxx {
public void dummy_method(){
replDumpId = incrementalDumpId;
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data, driver);
verifyRun("SELECT a from " + dbName + "_dupe.unptned ORDER BY a", unptn_data, driverMirror);
run("TRUNCATE TABLE " + dbName + ".unptned", driver);
verifySetup("SELECT a from " + dbName + ".unptned", empty, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};