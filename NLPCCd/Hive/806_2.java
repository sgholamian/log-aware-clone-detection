//,temp,sample_1817.java,2,16,temp,sample_1849.java,2,16
//,3
public class xxx {
public void dummy_method(){
String[] unptn_data = new String[] { "thirteen" };
run("INSERT INTO TABLE " + dbName + ".unptned values('" + unptn_data[0] + "')", driver);
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String insertDumpId = getResult(0, 1, false, driver);
String[] data_after_ovwrite = new String[] { "hundred" };
run("INSERT OVERWRITE TABLE " + dbName + ".unptned values('" + data_after_ovwrite[0] + "')", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + insertDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};