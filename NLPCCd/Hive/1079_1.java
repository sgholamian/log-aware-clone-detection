//,temp,sample_1831.java,2,16,temp,sample_1808.java,2,16
//,3
public class xxx {
public void dummy_method(){
String[] unptn_data_after_ins = new String[] { "eleven", "thirteen", "twelve" };
String[] data_after_ovwrite = new String[] { "hundred" };
run("INSERT INTO TABLE " + dbName + ".unptned_late values('" + unptn_data_after_ins[1] + "')", driver);
verifySetup("SELECT a from " + dbName + ".unptned_late ORDER BY a", unptn_data_after_ins, driver);
run("INSERT OVERWRITE TABLE " + dbName + ".unptned values('" + data_after_ovwrite[0] + "')", driver);
verifySetup("SELECT a from " + dbName + ".unptned", data_after_ovwrite, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};