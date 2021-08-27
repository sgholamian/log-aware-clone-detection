//,temp,sample_27.java,2,16,temp,sample_19.java,2,16
//,3
public class xxx {
public void dummy_method(){
List<String> rs = runStatementOnDriver("select a, b, INPUT__FILE__NAME from " + Table.NONACIDNONBUCKET + " order by a, b, INPUT__FILE__NAME", confForTez);
String expected0[][] = {
{"1\t2", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "1/000000_0"}, {"3\t4", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "1/000000_0"}, {"5\t6", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "1/000000_0"}, {"5\t6", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "2/000000_0"}, {"7\t8", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "2/000000_0"}, {"9\t10", AbstractFileMergeOperator.UNION_SUDBIR_PREFIX + "2/000000_0"}, };
Assert.assertEquals("Unexpected row count after ctas", expected0.length, rs.size());
for(int i = 0; i < expected0.length; i++) {
Assert.assertTrue("Actual line " + i + " bc: " + rs.get(i), rs.get(i).startsWith(expected0[i][0]));
Assert.assertTrue("Actual line(file) " + i + " bc: " + rs.get(i), rs.get(i).endsWith(expected0[i][1]));
}
runStatementOnDriver("alter table " + Table.NONACIDNONBUCKET + " SET TBLPROPERTIES ('transactional'='true')", confForTez);
rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " + Table.NONACIDNONBUCKET + " order by ROW__ID", confForTez);


log.info("after ctas");
}

};