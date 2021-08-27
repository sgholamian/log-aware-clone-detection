//,temp,sample_1897.java,2,16,temp,sample_1865.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("CREATE TABLE " + dbName + ".ptned2(a int) partitioned by (b int) STORED AS TEXTFILE", driver);
run("LOAD DATA LOCAL INPATH '" + ptn_locn_1 + "' OVERWRITE INTO TABLE " + dbName + ".ptned2 PARTITION(b=1)", driver);
run("ANALYZE TABLE " + dbName + ".unptned2 COMPUTE STATISTICS FOR COLUMNS", driver);
run("ANALYZE TABLE " + dbName + ".unptned2 COMPUTE STATISTICS", driver);
run("ANALYZE TABLE " + dbName + ".ptned2 partition(b) COMPUTE STATISTICS FOR COLUMNS", driver);
run("ANALYZE TABLE " + dbName + ".ptned2 partition(b) COMPUTE STATISTICS", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0,0,driver);
String incrementalDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};