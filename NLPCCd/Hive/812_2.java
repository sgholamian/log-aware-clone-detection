//,temp,sample_1898.java,2,14,temp,sample_1848.java,2,14
//,3
public class xxx {
public void testInsertOverwriteOnUnpartitionedTableWithCM() throws IOException {
String testName = "insertOverwriteOnUnpartitionedTableWithCM";
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