//,temp,sample_1835.java,2,16,temp,sample_1884.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".ptned_2 PARTITION(b=20) values('" + ptn_data_2[1] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned_2 PARTITION(b=20) values('" + ptn_data_2[2] + "')", driver);
verifyRun("SELECT a from " + dbName + ".ptned_1 where (b=1) ORDER BY a", ptn_data_1, driver);
verifyRun("SELECT a from " + dbName + ".ptned_1 where (b=2) ORDER BY a", ptn_data_2, driver);
verifyRun("SELECT a from " + dbName + ".ptned_2 where (b=10) ORDER BY a", ptn_data_1, driver);
verifyRun("SELECT a from " + dbName + ".ptned_2 where (b=20) ORDER BY a", ptn_data_2, driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};