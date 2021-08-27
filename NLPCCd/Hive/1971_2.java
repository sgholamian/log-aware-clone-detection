//,temp,sample_1853.java,2,16,temp,sample_1871.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[1] + "')", driver);
String secondInsertLastReplId = replDumpDb(dbName, firstInsertLastReplId, null, null).lastReplId;
Integer numOfEventsIns2 = Integer.valueOf(secondInsertLastReplId) - Integer.valueOf(firstInsertLastReplId);
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[2] + "')", driver);
verifyRun("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data, driver);
run("REPL LOAD " + dbName + "_dupe FROM '" + replDumpLocn + "'", driverMirror);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " LIMIT " + numOfEventsIns1, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};