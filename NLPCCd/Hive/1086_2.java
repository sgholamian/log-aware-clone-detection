//,temp,sample_1866.java,2,16,temp,sample_1839.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT name from " + dbName + ".namelist where (year=1980) ORDER BY name", ptn_year_1980, driver);
verifySetup("SELECT name from " + dbName + ".namelist where (day=1) ORDER BY name", ptn_day_1_2, driver);
verifySetup("SELECT name from " + dbName + ".namelist where (year=1984 and month=4 and day=1) ORDER BY name", ptn_year_1984_month_4_day_1_2, driver);
verifySetup("SELECT name from " + dbName + ".namelist ORDER BY name", ptn_data_2, driver);
verifyRun("SHOW PARTITIONS " + dbName + ".namelist", ptn_list_2, driver);
verifyRunWithPatternMatch("SHOW TABLE EXTENDED LIKE namelist PARTITION (year=1990,month=5,day=25)", "location", "namelist/year=1990/month=5/day=25", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};