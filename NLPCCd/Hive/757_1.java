//,temp,sample_1900.java,2,16,temp,sample_1899.java,2,16
//,3
public class xxx {
public void dummy_method(){
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);
run("REPL LOAD " + dbName + "_dupe FROM '"+incrementalDumpLocn+"'", driverMirror);
verifyIfTableNotExist(dbName + "_dupe", "acid_table_rename", metaStoreClientMirror);
run("CREATE TABLE " + dbName + ".acid_table_incremental (key int, value int) PARTITIONED BY (load_date date) " + "CLUSTERED BY(key) INTO 2 BUCKETS STORED AS ORC TBLPROPERTIES ('transactional'='true')", driver);
verifyIfTableExist(dbName, "acid_table_incremental", metaStoreClient);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + incrementalDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id");
}

};