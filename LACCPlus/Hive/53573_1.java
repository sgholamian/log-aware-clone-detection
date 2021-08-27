//,temp,TestAcidOnTez.java,576,644,temp,TestTxnNoBuckets.java,535,627
//,3
public class xxx {
  @Test
  public void testInsertWithRemoveUnion() throws Exception {
    int[][] values = {{1,2},{3,4},{5,6},{7,8},{9,10}};
    HiveConf confForTez = new HiveConf(hiveConf); // make a clone of existing hive conf
    setupTez(confForTez);
    runStatementOnDriver("drop table if exists T", confForTez);
    runStatementOnDriver("create table T (a int, b int) stored as ORC  TBLPROPERTIES ('transactional'='false')", confForTez);
    /*
ekoifman:apache-hive-3.0.0-SNAPSHOT-bin ekoifman$ tree  ~/dev/hiverwgit/itests/hive-unit/target/tmp/org.apache.hadoop.hive.ql.TestAcidOnTez-1505502329802/warehouse/t/.hive-staging_hive_2017-09-15_12-07-33_224_7717909516029836949-1/
/Users/ekoifman/dev/hiverwgit/itests/hive-unit/target/tmp/org.apache.hadoop.hive.ql.TestAcidOnTez-1505502329802/warehouse/t/.hive-staging_hive_2017-09-15_12-07-33_224_7717909516029836949-1/
└── -ext-10000
    ├── HIVE_UNION_SUBDIR_1
    │   └── 000000_0
    ├── HIVE_UNION_SUBDIR_2
    │   └── 000000_0
    └── HIVE_UNION_SUBDIR_3
        └── 000000_0

4 directories, 3 files
     */
    runStatementOnDriver("insert into T(a,b) select a, b from " + Table.ACIDTBL + " where a between 1 and 3 group by a, b union all select a, b from " + Table.ACIDTBL + " where a between 5 and 7 union all select a, b from " + Table.ACIDTBL + " where a >= 9", confForTez);
    List<String> rs = runStatementOnDriver("select a, b, INPUT__FILE__NAME from T order by a, b, INPUT__FILE__NAME", confForTez);
    LOG.warn(testName.getMethodName() + ": before converting to acid");
    for(String s : rs) {
      LOG.warn(s);
    }
    String[][] expected = {
      {"1\t2","warehouse/t/" + AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "1/000000_0"},
      {"3\t4","warehouse/t/" + AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "1/000000_0"},
      {"5\t6","warehouse/t/" + AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "2/000000_0"},
      {"7\t8","warehouse/t/" + AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "2/000000_0"},
      {"9\t10","warehouse/t/" + AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "3/000000_0"}
    };
    Assert.assertEquals("Unexpected row count after conversion", expected.length, rs.size());
    for(int i = 0; i < expected.length; i++) {
      Assert.assertTrue("Actual line " + i + " bc: " + rs.get(i), rs.get(i).startsWith(expected[i][0]));
      Assert.assertTrue("Actual line(file) " + i + " bc: " + rs.get(i), rs.get(i).endsWith(expected[i][1]));
    }
    //make the table ACID
    runStatementOnDriver("alter table T SET TBLPROPERTIES ('transactional'='true')", confForTez);
    rs = runStatementOnDriver("select a,b from T order by a, b", confForTez);
    Assert.assertEquals("After to Acid conversion", TestTxnCommands2.stringifyValues(values), rs);

    //run Major compaction
    runStatementOnDriver("alter table T compact 'major'", confForTez);
    TestTxnCommands2.runWorker(hiveConf);
    rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from T order by ROW__ID", confForTez);
    LOG.warn(testName.getMethodName() + ": after compact major of T:");
    for (String s : rs) {
      LOG.warn(s);
    }
    String[][] expected2 = {
       {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":0}\t1\t2",
           "warehouse/t/base_-9223372036854775808_v0000023/bucket_00000"},
      {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":1}\t3\t4",
          "warehouse/t/base_-9223372036854775808_v0000023/bucket_00000"},
      {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":2}\t5\t6",
          "warehouse/t/base_-9223372036854775808_v0000023/bucket_00000"},
      {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":3}\t7\t8",
          "warehouse/t/base_-9223372036854775808_v0000023/bucket_00000"},
      {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":4}\t9\t10",
          "warehouse/t/base_-9223372036854775808_v0000023/bucket_00000"}
    };
    Assert.assertEquals("Unexpected row count after major compact", expected2.length, rs.size());
    for(int i = 0; i < expected2.length; i++) {
      Assert.assertTrue("Actual line " + i + " ac: " + rs.get(i), rs.get(i).startsWith(expected2[i][0]));
      Assert.assertTrue("Actual line(file) " + i + " ac: " + rs.get(i), rs.get(i).endsWith(expected2[i][1]));
    }
  }

};