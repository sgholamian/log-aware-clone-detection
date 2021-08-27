//,temp,sample_1867.java,2,16,temp,sample_1881.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyRun("SELECT * from " + dbName + "_dupe.virtual_view", empty, driverMirror);
verifyRun("SELECT a from " + dbName + "_dupe.mat_view", ptn_data_1, driverMirror);
verifyRun("SELECT * from " + dbName + "_dupe.virtual_view2", empty, driverMirror);
verifyRun("SELECT * from " + dbName + "_dupe.mat_view2", unptn_data, driverMirror);
run("ALTER VIEW " + dbName + ".virtual_view RENAME TO " + dbName + ".virtual_view_rename", driver);
verifySetup("SELECT * from " + dbName + ".virtual_view_rename", unptn_data, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + incrementalDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};