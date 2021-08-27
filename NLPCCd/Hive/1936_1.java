//,temp,sample_27.java,2,16,temp,sample_19.java,2,16
//,3
public class xxx {
public void dummy_method(){
String expected[][] = {
{"{\"transactionid\":18,\"bucketid\":536870913,\"rowid\":0}\t1\t2", "/delta_0000018_0000018_0001/bucket_00000"}, {"{\"transactionid\":18,\"bucketid\":536870913,\"rowid\":1}\t3\t4", "/delta_0000018_0000018_0001/bucket_00000"}, {"{\"transactionid\":18,\"bucketid\":536870913,\"rowid\":2}\t5\t6", "/delta_0000018_0000018_0001/bucket_00000"}, {"{\"transactionid\":18,\"bucketid\":536870914,\"rowid\":0}\t9\t10", "/delta_0000018_0000018_0002/bucket_00000"}, {"{\"transactionid\":18,\"bucketid\":536870914,\"rowid\":1}\t7\t8", "/delta_0000018_0000018_0002/bucket_00000"}, {"{\"transactionid\":18,\"bucketid\":536870914,\"rowid\":2}\t5\t6", "/delta_0000018_0000018_0002/bucket_00000"}, };
Assert.assertEquals("Unexpected row count after ctas", expected.length, rs.size());
for(int i = 0; i < expected.length; i++) {
Assert.assertTrue("Actual line " + i + " bc: " + rs.get(i), rs.get(i).startsWith(expected[i][0]));
Assert.assertTrue("Actual line(file) " + i + " bc: " + rs.get(i), rs.get(i).endsWith(expected[i][1]));
}
runStatementOnDriver("update " + Table.ACIDNOBUCKET + " set a = 70, b  = 80 where a = 7", confForTez);
runStatementOnDriver("delete from " + Table.ACIDNOBUCKET + " where a = 5", confForTez);
rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " + Table.ACIDNOBUCKET + " order by ROW__ID", confForTez);


log.info("after update delete");
}

};