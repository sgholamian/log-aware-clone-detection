//,temp,sample_1872.java,2,16,temp,sample_1888.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data, driver);
verifyRun("SELECT a from " + dbName + "_dupe.unptned ORDER BY a", unptn_data_load1, driverMirror);
advanceDumpDir();
Integer lastReplID = Integer.valueOf(replDumpId);
lastReplID += 1000;
String toReplID = String.valueOf(lastReplID);
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + toReplID + " LIMIT " + numOfEventsIns2, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};