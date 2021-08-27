//,temp,sample_4768.java,2,11,temp,sample_3335.java,2,11
//,2
public class xxx {
public void testToAcidConversion02() throws Exception {
runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(1,2),(1,3)");
runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(0,12),(0,13),(1,4),(1,5)");
runStatementOnDriver("insert into " + Table.NONACIDNONBUCKET + "(a,b) values(1,6)");
runStatementOnDriver("alter table " + Table.NONACIDNONBUCKET + " SET TBLPROPERTIES ('transactional'='true', 'transactional_properties'='default')");
List<String> rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " +  Table.NONACIDNONBUCKET + " order by ROW__ID");


log.info("before acid ops after convert");
}

};