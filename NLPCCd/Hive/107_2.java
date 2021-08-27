//,temp,sample_1834.java,2,14,temp,sample_1879.java,2,14
//,2
public class xxx {
public void testTruncateTable() throws IOException {
String testName = "truncateTable";
String dbName = testName + "_" + tid;
run("CREATE DATABASE " + dbName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};