//,temp,sample_3332.java,2,16,temp,sample_4769.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertTrue(rs.get(0), rs.get(0).endsWith("nonacidorctbl/000000_0_copy_1"));
Assert.assertTrue(rs.get(1), rs.get(1).startsWith("{\"transactionid\":0,\"bucketid\":536936448,\"rowid\":0}\t1\t2"));
Assert.assertTrue(rs.get(1), rs.get(1).endsWith("nonacidorctbl/000001_0"));
Assert.assertTrue(rs.get(2), rs.get(2).startsWith("{\"transactionid\":0,\"bucketid\":536936448,\"rowid\":1}\t1\t5"));
Assert.assertTrue(rs.get(2), rs.get(2).endsWith("nonacidorctbl/000001_0_copy_1"));
Assert.assertTrue(rs.get(3), rs.get(3).startsWith("{\"transactionid\":16,\"bucketid\":536936448,\"rowid\":0}\t1\t17"));
Assert.assertTrue(rs.get(3), rs.get(3).endsWith("nonacidorctbl/delta_0000016_0000016_0000/bucket_00001"));
runStatementOnDriver("alter table "+ TestTxnCommands2.Table.NONACIDORCTBL +" compact 'major'");
TestTxnCommands2.runWorker(hiveConf);
rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " + Table.NONACIDORCTBL + " order by ROW__ID");


log.info("after compact");
}

};