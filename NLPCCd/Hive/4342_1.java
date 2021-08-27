//,temp,sample_1829.java,2,13,temp,sample_1856.java,2,15
//,3
public class xxx {
public void testIncrementalInserts() throws IOException {
String testName = "incrementalInserts";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};