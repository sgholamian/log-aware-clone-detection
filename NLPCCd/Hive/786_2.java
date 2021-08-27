//,temp,sample_1850.java,2,16,temp,sample_1868.java,2,16
//,3
public class xxx {
public void dummy_method(){
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);
run("REPL LOAD " + dbName + "_dupe FROM '" + incrementalDumpLocn + "'", driverMirror);
verifyRun("SELECT * from " + dbName + "_dupe.virtual_view_rename", empty, driverMirror);
run("ALTER VIEW " + dbName + ".virtual_view_rename AS SELECT a, concat(a, '_') as a_ FROM " + dbName + ".unptned", driver);
verifySetup("SHOW COLUMNS FROM " + dbName + ".virtual_view_rename", new String[] {"a", "a_"}, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + incrementalDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};