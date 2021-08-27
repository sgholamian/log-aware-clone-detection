//,temp,sample_1900.java,2,16,temp,sample_1899.java,2,16
//,3
public class xxx {
public void dummy_method(){
String replDumpLocn = getResult(0, 0,driver);
String replDumpId = getResult(0, 1, true, driver);
run("REPL LOAD " + dbName + "_dupe FROM '" + replDumpLocn + "'", driverMirror);
verifyIfTableNotExist(dbName + "_dupe", "acid_table", metaStoreClientMirror);
run("ALTER TABLE " + dbName + ".acid_table RENAME TO " + dbName + ".acid_table_rename", driver);
verifyIfTableExist(dbName, "acid_table_rename", metaStoreClient);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};