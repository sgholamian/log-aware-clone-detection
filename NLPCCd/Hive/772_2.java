//,temp,sample_1901.java,2,16,temp,sample_1870.java,2,13
//,3
public class xxx {
public void testDumpLimit() throws IOException {
String name = testName.getMethodName();
String dbName = createDB(name, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};