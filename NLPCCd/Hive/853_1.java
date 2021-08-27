//,temp,sample_1819.java,2,14,temp,sample_1841.java,2,13
//,3
public class xxx {
public void testIncrementalLoadWithVariableLengthEventId() throws IOException, TException {
String testName = "incrementalLoadWithVariableLengthEventId";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
run("INSERT INTO TABLE " + dbName + ".unptned values('ten')", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};