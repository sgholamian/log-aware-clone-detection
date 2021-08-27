//,temp,TestReplicationScenarios.java,2902,2935,temp,TestReplicationScenarios.java,2867,2900
//,2
public class xxx {
  @Test
  public void testRenamePartitionedTableAcrossDatabases() throws IOException {
    String testName = "renamePartitionedTableAcrossDatabases";
    LOG.info("Testing " + testName);
    String dbName1 = testName + "_" + tid + "_1";
    String dbName2 = testName + "_" + tid + "_2";
    String replDbName1 = dbName1 + "_dupe";
    String replDbName2 = dbName2 + "_dupe";

    createDB(dbName1, driver);
    createDB(dbName2, driver);
    run("CREATE TABLE " + dbName1 + ".ptned(a string) partitioned by (b int) STORED AS TEXTFILE", driver);

    String[] ptn_data = new String[] { "fifteen", "fourteen" };
    String ptn_locn = new Path(TEST_PATH, testName + "_ptn").toUri().getPath();

    createTestDataFile(ptn_locn, ptn_data);
    run("LOAD DATA LOCAL INPATH '" + ptn_locn + "' OVERWRITE INTO TABLE " + dbName1 + ".ptned PARTITION(b=1)", driver);

    Tuple bootstrap1 = bootstrapLoadAndVerify(dbName1, replDbName1);
    Tuple bootstrap2 = bootstrapLoadAndVerify(dbName2, replDbName2);

    verifyRun("SELECT a from " + replDbName1 + ".ptned where (b=1) ORDER BY a", ptn_data, driverMirror);
    verifyIfTableNotExist(replDbName2, "ptned", metaStoreClientMirror);

    verifyFail("ALTER TABLE " + dbName1 + ".ptned RENAME TO " + dbName2 + ".ptned_renamed", driver);

    incrementalLoadAndVerify(dbName1, replDbName1);
    incrementalLoadAndVerify(dbName2, replDbName2);

    verifyIfTableNotExist(replDbName1, "ptned_renamed", metaStoreClientMirror);
    verifyIfTableNotExist(replDbName2, "ptned_renamed", metaStoreClientMirror);
    verifyRun("SELECT a from " + replDbName1 + ".ptned where (b=1) ORDER BY a", ptn_data, driverMirror);
  }

};