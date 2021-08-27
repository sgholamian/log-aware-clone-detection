//,temp,sample_1876.java,2,16,temp,sample_1882.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT a from " + dbName + ".unptned", empty, driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.unptned", empty, driverMirror);
String[] unptn_data_after_ins = new String[] { "thirteen" };
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data_after_ins[0] + "')", driver);
verifySetup("SELECT a from " + dbName + ".unptned ORDER BY a", unptn_data_after_ins, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};