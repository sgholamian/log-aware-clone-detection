//,temp,sample_1835.java,2,16,temp,sample_1884.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("ALTER TABLE " + dbName + ".ptned ADD PARTITION (b=2)", driver);
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[0] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[1] + "')", driver);
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[2] + "')", driver);
verifySetup("SELECT a from " + dbName + ".ptned where (b=1) ORDER BY a", ptn_data_1, driver);
verifySetup("SELECT a from " + dbName + ".ptned where (b=2) ORDER BY a", ptn_data_2, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};