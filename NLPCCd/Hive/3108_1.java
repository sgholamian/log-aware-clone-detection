//,temp,sample_4768.java,2,11,temp,sample_3335.java,2,11
//,2
public class xxx {
public void testNonAcidToAcidConversion01() throws Exception {
runStatementOnDriver("insert into " + Table.NONACIDORCTBL + "(a,b) values(1,2)");
runStatementOnDriver("insert into " + Table.NONACIDORCTBL + "(a,b) values(0,12),(1,5)");
runStatementOnDriver("alter table " + Table.NONACIDORCTBL + " SET TBLPROPERTIES ('transactional'='true')");
runStatementOnDriver("insert into " + Table.NONACIDORCTBL + "(a,b) values(1,17)");
List<String> rs = runStatementOnDriver("select ROW__ID, a, b, INPUT__FILE__NAME from " +  Table.NONACIDORCTBL + " order by ROW__ID");


log.info("before compact");
}

};