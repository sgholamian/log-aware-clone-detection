//,temp,sample_1844.java,2,13,temp,sample_1860.java,2,14
//,3
public class xxx {
public void testRenamePartitionWithCM() throws IOException {
String testName = "renamePartitionWithCM";
String dbName = testName + "_" + tid;
run("CREATE DATABASE " + dbName, driver);
run("CREATE TABLE " + dbName + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};