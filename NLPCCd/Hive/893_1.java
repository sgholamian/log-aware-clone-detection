//,temp,sample_1842.java,2,16,temp,sample_1830.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String lastDumpIdWithoutDrop = getResult(0, 1, driver);
run("DROP TABLE " + dbName + ".unptned", driver);
run("DROP TABLE " + dbName + ".unptned_tmp", driver);
verifyFail("SELECT * FROM " + dbName + ".unptned", driver);
verifyFail("SELECT * FROM " + dbName + ".unptned_tmp", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + lastDumpIdWithoutDrop, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};