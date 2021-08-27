//,temp,sample_1857.java,2,16,temp,sample_1875.java,2,16
//,3
public class xxx {
public void dummy_method(){
run("INSERT INTO TABLE " + dbName + ".ptned partition(b=2) values('" + ptn_data_2[1] + "')", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId, driver);
String lastDumpIdWithoutRename = getResult(0, 1, driver);
run("ALTER TABLE " + dbName + ".unptned RENAME TO " + dbName + ".unptned_renamed", driver);
run("ALTER TABLE " + dbName + ".ptned RENAME TO " + dbName + ".ptned_renamed", driver);
advanceDumpDir();
run("REPL DUMP " + dbName + " FROM " + replDumpId + " TO " + lastDumpIdWithoutRename, driver);
String incrementalDumpLocn = getResult(0, 0, driver);
String incrementalDumpId = getResult(0, 1, true, driver);


log.info("incremental dump dumped to with id from");
}

};