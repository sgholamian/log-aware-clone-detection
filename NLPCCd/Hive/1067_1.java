//,temp,sample_3332.java,2,16,temp,sample_4769.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertTrue(rs.get(0), rs.get(0).startsWith("{\"transactionid\":19,\"bucketid\":536870912,\"rowid\":0}\t0\t0\t0\t"));
Assert.assertTrue(rs.get(0), rs.get(0).endsWith("nobuckets/delta_0000019_0000019_0000/bucket_00000"));
Assert.assertTrue(rs.get(1), rs.get(1).startsWith("{\"transactionid\":19,\"bucketid\":536870912,\"rowid\":1}\t3\t3\t3\t"));
Assert.assertTrue(rs.get(1), rs.get(1).endsWith("nobuckets/delta_0000019_0000019_0000/bucket_00000"));
Assert.assertTrue(rs.get(2), rs.get(2).startsWith("{\"transactionid\":19,\"bucketid\":536936448,\"rowid\":0}\t1\t1\t1\t"));
Assert.assertTrue(rs.get(2), rs.get(2).endsWith("nobuckets/delta_0000019_0000019_0000/bucket_00001"));
Assert.assertTrue(rs.get(3), rs.get(3).startsWith("{\"transactionid\":19,\"bucketid\":536936448,\"rowid\":1}\t2\t2\t2\t"));
Assert.assertTrue(rs.get(3), rs.get(3).endsWith("nobuckets/delta_0000019_0000019_0000/bucket_00001"));
runStatementOnDriver("update nobuckets set c3 = 17 where c3 in(0,1)");
rs = runStatementOnDriver("select ROW__ID, c1, c2, c3, INPUT__FILE__NAME from nobuckets order by INPUT__FILE__NAME, ROW__ID");


log.info("after update");
}

};