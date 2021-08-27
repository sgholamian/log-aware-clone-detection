//,temp,sample_1887.java,2,14,temp,sample_1852.java,2,14
//,2
public class xxx {
public void testInsertOverwriteOnPartitionedTableWithCM() throws IOException {
String testName = "insertOverwriteOnPartitionedTableWithCM";
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