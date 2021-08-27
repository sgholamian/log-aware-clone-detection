//,temp,sample_1819.java,2,14,temp,sample_1841.java,2,13
//,3
public class xxx {
public void testIncrementalInsertDropUnpartitionedTable() throws IOException {
String testName = "incrementalInsertDropUnpartitionedTable";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};