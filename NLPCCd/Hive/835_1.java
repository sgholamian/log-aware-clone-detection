//,temp,sample_1903.java,2,16,temp,sample_1845.java,2,16
//,3
public class xxx {
public void dummy_method(){
String testName = "cmConflict";
String dbName = createDB(testName, driver);
run("CREATE TABLE " + dbName + ".unptned(a string) STORED AS TEXTFILE", driver);
run("INSERT INTO TABLE " + dbName + ".unptned values('ten')", driver);
run("INSERT INTO TABLE " + dbName + ".unptned values('ten')", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0,driver);
String replDumpId = getResult(0, 1, true, driver);
run("TRUNCATE TABLE " + dbName + ".unptned", driver);


log.info("bootstrap dump dumped to with id");
}

};