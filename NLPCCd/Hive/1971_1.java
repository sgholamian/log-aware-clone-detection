//,temp,sample_1853.java,2,16,temp,sample_1871.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[1] + "')", driver);
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String insertDumpId = getResult(0, 1, false, driver);
String[] data_after_ovwrite = new String[] { "hundred" };
run("INSERT OVERWRITE TABLE " + dbName + ".ptned partition(b=2) values('" + data_after_ovwrite[0] + "')", driver);
verifySetup("SELECT a from " + dbName + ".ptned where (b=2)", data_after_ovwrite, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + insertDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};