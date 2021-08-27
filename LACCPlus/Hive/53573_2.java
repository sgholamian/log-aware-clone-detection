//,temp,TestAcidOnTez.java,576,644,temp,TestTxnNoBuckets.java,535,627
//,3
public class xxx {
  @Test
  public void testToAcidConversion02() throws Exception {
    //create 2 rows in a file 00000_0
    runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(1,2),(1,3)");
    //create 4 rows in a file 000000_0_copy_1
    runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(0,12),(0,13),(1,4),(1,5)");
    //create 1 row in a file 000000_0_copy_2
    runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(1,6)");

    //convert the table to Acid  //todo: remove trans_prop after HIVE-17089
    runStatementOnDriver("alter table " + Table.NONACIDNONBUCKET + " SET TBLPROPERTIES ('transactional'='true', 'transactional_properties'='default')");
    List<String> rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " +  Table.NONACIDNONBUCKET + " order by ROW__ID");
    LOG.warn("before acid ops (after convert)");
    for(String s : rs) {
      LOG.warn(s);
    }
    //create a some of delta directories
    runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(0,15),(1,16)");
    runStatementOnDriver("update " + Table.NONACIDNONBUCKET + " set b = 120 where a = 0 and b = 12");
    runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(0,17)");
    runStatementOnDriver("delete from " + Table.NONACIDNONBUCKET + " where a = 1 and b = 3");

    rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " +  Table.NONACIDNONBUCKET + " order by a,b");
    LOG.warn("before compact");
    for(String s : rs) {
      LOG.warn(s);
    }
    Assert.assertEquals(0, BucketCodec.determineVersion(536870912).decodeWriterId(536870912));
    /*
     * All ROW__IDs are unique on read after conversion to acid
     * ROW__IDs are exactly the same before and after compaction
     * Also check the file name (only) after compaction for completeness
     */
    String[][] expected = {
        {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":3}\t0\t13",
            "bucket_00000", "000000_0_copy_1"},
        {"{\"writeid\":10000001,\"bucketid\":536870912,\"rowid\":0}\t0\t15",
            "bucket_00000", "bucket_00000_0"},
        {"{\"writeid\":10000003,\"bucketid\":536870912,\"rowid\":0}\t0\t17",
            "bucket_00000", "bucket_00000_0"},
        {"{\"writeid\":10000002,\"bucketid\":536870912,\"rowid\":0}\t0\t120",
            "bucket_00000", "bucket_00000_0"},
        {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":0}\t1\t2",
            "bucket_00000", "000000_0"},
        {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":4}\t1\t4",
            "bucket_00000", "000000_0_copy_1"},
        {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":5}\t1\t5",
            "bucket_00000", "000000_0_copy_1"},
        {"{\"writeid\":0,\"bucketid\":536870912,\"rowid\":6}\t1\t6",
            "bucket_00000", "000000_0_copy_2"},
        {"{\"writeid\":10000001,\"bucketid\":536870912,\"rowid\":1}\t1\t16",
            "bucket_00000", "bucket_00000_0"}
    };
    Assert.assertEquals("Unexpected row count before compaction", expected.length, rs.size());
    for(int i = 0; i < expected.length; i++) {
      Assert.assertTrue("Actual line " + i + " bc: " + rs.get(i), rs.get(i).startsWith(expected[i][0]));
      Assert.assertTrue("Actual line(file) " + i + " bc: " + rs.get(i), rs.get(i).endsWith(expected[i][2]));
    }
    //run Compaction
    runStatementOnDriver("alter table "+ Table.NONACIDNONBUCKET +" compact 'major'");
    TestTxnCommands2.runWorker(hiveConf);
    /*
    nonacidnonbucket/
    ├── 000000_0
    ├── 000000_0_copy_1
    ├── 000000_0_copy_2
    ├── base_0000004
    │   └── bucket_00000
    ├── delete_delta_0000002_0000002_0000
    │   └── bucket_00000
    ├── delete_delta_0000004_0000004_0000
    │   └── bucket_00000
    ├── delta_0000001_0000001_0000
    │   └── bucket_00000
    ├── delta_0000002_0000002_0000
    │   └── bucket_00000
    └── delta_0000003_0000003_0000
        └── bucket_00000

    6 directories, 9 files
    */
    rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " + Table.NONACIDNONBUCKET + " order by a,b");
    LOG.warn("after compact");
    for(String s : rs) {
      LOG.warn(s);
    }
    Assert.assertEquals("Unexpected row count after compaction", expected.length, rs.size());
    for(int i = 0; i < expected.length; i++) {
      Assert.assertTrue("Actual line " + i + " ac: " + rs.get(i), rs.get(i).startsWith(expected[i][0]));
      Assert.assertTrue("Actual line(file) " + i + " ac: " + rs.get(i), rs.get(i).endsWith(expected[i][1]));
    }
    //make sure they are the same before and after compaction
  }

};