//,temp,sample_1826.java,2,16,temp,sample_1861.java,2,16
//,3
public class xxx {
public void dummy_method(){
String testName = "incrementalLoad";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
run("CREATE TABLE " + dbName + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);
run("CREATE TABLE " + dbName + ".unptned_empty(a string) STORED AS TEXTFILE", driver);
run("CREATE TABLE " + dbName + ".ptned_empty(a string) partitioned by (b int) STORED AS TEXTFILE", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0,driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};