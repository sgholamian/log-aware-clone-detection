//,temp,sample_1893.java,2,16,temp,sample_1880.java,2,16
//,3
public class xxx {
public void dummy_method(){
String testName = "constraints";
String dbName = testName + "_" + tid;
run("CREATE DATABASE " + dbName, driver);
run("CREATE TABLE " + dbName + ".tbl1(a string, b string, primary key (a, b) disable novalidate rely)", driver);
run("CREATE TABLE " + dbName + ".tbl2(a string, b string, foreign key (a, b) references " + dbName + ".tbl1(a, b) disable novalidate)", driver);
run("CREATE TABLE " + dbName + ".tbl3(a string, b string not null disable, unique (a) disable)", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("dumped to with id");
}

};