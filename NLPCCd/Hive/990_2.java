//,temp,sample_1897.java,2,16,temp,sample_1865.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("LOAD DATA LOCAL INPATH '" + ptn_locn_1 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=1)", driver);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=1", ptn_data_1, driver);
run("LOAD DATA LOCAL INPATH '" + ptn_locn_2 + "' OVERWRITE INTO TABLE " + dbName + ".ptned PARTITION(b=2)", driver);
verifySetup("SELECT a from " + dbName + ".ptned WHERE b=2", ptn_data_2, driver);
run("CREATE MATERIALIZED VIEW " + dbName + ".mat_view AS SELECT a FROM " + dbName + ".ptned where b=1", driver);
verifySetup("SELECT a from " + dbName + ".mat_view", ptn_data_1, driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0,0,driver);
String replDumpId = getResult(0,1,true,driver);


log.info("bootstrap dump dumped to with id");
}

};