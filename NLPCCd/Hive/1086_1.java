//,temp,sample_1866.java,2,16,temp,sample_1839.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyRun("SELECT a from " + dbName + "_dupe.mat_view", ptn_data_1, driverMirror);
run("CREATE VIEW " + dbName + ".virtual_view2 AS SELECT a FROM " + dbName + ".ptned where b=2", driver);
verifySetup("SELECT a from " + dbName + ".virtual_view2", ptn_data_2, driver);
run("CREATE VIEW " + dbName + ".virtual_view2 AS SELECT a FROM " + dbName + ".ptned where b=2", driver);
run("CREATE MATERIALIZED VIEW " + dbName + ".mat_view2 AS SELECT * FROM " + dbName + ".unptned", driver);
verifySetup("SELECT * from " + dbName + ".mat_view2", unptn_data, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0,0,driver);
String incrementalDumpId = getResult(0,1,true,driver);


log.info("incremental dump dumped to with id");
}

};