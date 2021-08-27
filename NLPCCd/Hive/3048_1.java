//,temp,sample_1818.java,2,16,temp,sample_1846.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=2", ptn_data_2, driver);
run("CREATE TABLE " + dbName + ".ptned_late(a string) PARTITIONED BY (b int) STORED AS TEXTFILE", driver);
run("LOAD DATA LOCAL INPATH '" + ptn_locn_1 + "' OVERWRITE INTO TABLE " + dbName + ".ptned_late PARTITION(b=1)", driver);
verifySetup("SELECT a from " + dbName + ".ptned_late WHERE b=1",ptn_data_1, driver);
run("LOAD DATA LOCAL INPATH '" + ptn_locn_2 + "' OVERWRITE INTO TABLE " + dbName + ".ptned_late PARTITION(b=2)", driver);
verifySetup("SELECT a from " + dbName + ".ptned_late WHERE b=2", ptn_data_2, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0,0,driver);
String incrementalDumpId = getResult(0,1,true,driver);


log.info("dumped to with id");
}

};