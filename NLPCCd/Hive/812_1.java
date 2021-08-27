//,temp,sample_1898.java,2,14,temp,sample_1848.java,2,14
//,3
public class xxx {
public void testSkipTables() throws IOException {
String testName = "skipTables";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".acid_table (key int, value int) PARTITIONED BY (load_date date) " + "CLUSTERED BY(key) INTO 2 BUCKETS STORED AS ORC TBLPROPERTIES ('transactional'='true')", driver);
verifyIfTableExist(dbName, "acid_table", metaStoreClient);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0,driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};