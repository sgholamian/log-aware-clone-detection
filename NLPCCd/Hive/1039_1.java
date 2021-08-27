//,temp,sample_1836.java,2,16,temp,sample_1823.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifyRun("SELECT a from " + dbName + "_dupe.ptned where (b=2) ORDER BY a", ptn_data_2, driverMirror);
String[] data_after_ovwrite = new String[] { "hundred" };
run("INSERT OVERWRITE TABLE " + dbName + ".ptned partition(b=2) values('" + data_after_ovwrite[0] + "')", driver);
verifySetup("SELECT a from " + dbName + ".ptned where (b=2)", data_after_ovwrite, driver);
run("INSERT OVERWRITE TABLE " + dbName + ".ptned partition(b=3) values('" + data_after_ovwrite[0] + "')", driver);
verifySetup("SELECT a from " + dbName + ".ptned where (b=3)", data_after_ovwrite, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};