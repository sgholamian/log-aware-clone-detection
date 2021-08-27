//,temp,sample_1872.java,2,16,temp,sample_1888.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", empty, driver);
String thirdTruncLastReplId = replDumpDb(dbName, secondInsertLastReplId, null, null).lastReplId;
Integer numOfEventsTrunc3 = Integer.valueOf(thirdTruncLastReplId) - Integer.valueOf(secondInsertLastReplId);
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data_load1[0] + "')", driver);
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data_load1, driver);
run("REPL LOAD " + dbName + "_dupe FROM '" + replDumpLocn + "'", driverMirror);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " LIMIT " + numOfEventsIns1, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};