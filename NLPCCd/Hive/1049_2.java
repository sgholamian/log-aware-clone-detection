//,temp,sample_1896.java,2,16,temp,sample_1840.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("USE " + dbName, driverMirror);
String[] ptn_data_3 = new String[] { "abraham", "bob", "carter", "david", "fisher" };
String[] data_after_ovwrite = new String[] { "fisher" };
run("INSERT OVERWRITE TABLE " + dbName + ".namelist partition(year=1990,month=5,day=25) values('" + data_after_ovwrite[0] + "')", driver);
verifySetup("SELECT name from " + dbName + ".namelist where (year=1990 and month=5 and day=25)", data_after_ovwrite, driver);
verifySetup("SELECT name from " + dbName + ".namelist ORDER BY name", ptn_data_3, driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
incrementalDumpLocn = getResult(0, 0, driver);
incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};