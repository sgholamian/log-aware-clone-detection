//,temp,sample_1873.java,2,16,temp,sample_1869.java,2,16
//,3
public class xxx {
public void dummy_method(){
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SHOW COLUMNS FROM " + dbName + "_dupe.virtual_view_rename", new String[] {"a", "a_"}, driverMirror);
run("DROP VIEW " + dbName + ".virtual_view", driver);
verifyIfTableNotExist(dbName, "virtual_view", metaStoreClient);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + incrementalDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};