//,temp,sample_1806.java,2,16,temp,sample_1885.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("TRUNCATE TABLE " + dbName + ".ptned_1 PARTITION(b=2)", driver);
verifySetup("SELECT a from " + dbName + ".ptned_1 where (b=1) ORDER BY a", ptn_data_1, driver);
verifySetup("SELECT a from " + dbName + ".ptned_1 where (b=2)", empty, driver);
run("TRUNCATE TABLE " + dbName + ".ptned_2", driver);
verifySetup("SELECT a from " + dbName + ".ptned_2 where (b=10)", empty, driver);
verifySetup("SELECT a from " + dbName + ".ptned_2 where (b=20)", empty, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};