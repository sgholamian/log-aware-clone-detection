//,temp,sample_1838.java,2,16,temp,sample_1827.java,2,16
//,3
public class xxx {
public void dummy_method(){
verifySetup("SELECT name from " + dbName + ".namelist where (year=1980) ORDER BY name", ptn_year_1980, driver);
verifySetup("SELECT name from " + dbName + ".namelist where (day=1) ORDER BY name", ptn_day_1, driver);
verifySetup("SELECT name from " + dbName + ".namelist where (year=1984 and month=4 and day=1) ORDER BY name", ptn_year_1984_month_4_day_1_1, driver);
verifySetup("SELECT name from " + dbName + ".namelist ORDER BY name", ptn_data_1, driver);
verifySetup("SHOW PARTITIONS " + dbName + ".namelist", ptn_list_1, driver);
verifyRunWithPatternMatch("SHOW TABLE EXTENDED LIKE namelist PARTITION (year=1980,month=4,day=1)", "location", "namelist/year=1980/month=4/day=1", driver);
advanceDumpDir();
run("REPL DUMP " + dbName, driver);
String replDumpLocn = getResult(0, 0, driver);
String replDumpId = getResult(0, 1, true, driver);


log.info("bootstrap dump dumped to with id");
}

};