//,temp,sample_1818.java,2,16,temp,sample_1846.java,2,16
//,3
public class xxx {
public void dummy_method(){
replDumpId = incrementalDumpId;
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.ptned where (b=1) ORDER BY a", ptn_data_1, driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.ptned where (b=2) ORDER BY a", ptn_data_2, driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.ptned_tmp where (b=1) ORDER BY a", ptn_data_1, driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.ptned_tmp where (b=2) ORDER BY a", ptn_data_2, driverMirror);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};