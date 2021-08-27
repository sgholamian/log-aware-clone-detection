//,temp,sample_1903.java,2,16,temp,sample_1845.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String lastDumpIdWithoutDrop = getResult(0, 1, driver);
run("DROP TABLE " + dbName + ".ptned_tmp", driver);
run("DROP TABLE " + dbName + ".ptned", driver);
verifyFail("SELECT * FROM " + dbName + ".ptned_tmp", driver);
verifyFail("SELECT * FROM " + dbName + ".ptned", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + lastDumpIdWithoutDrop, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};