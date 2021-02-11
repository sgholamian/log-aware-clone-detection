//,temp,sample_8517.java,2,17,temp,sample_8509.java,2,17
//,3
public class xxx {
public void dummy_method(){
cstmt.registerOutParameter(8, java.sql.Types.BIGINT);
cstmt.registerOutParameter(9, java.sql.Types.VARCHAR);
long startTime = clock.getTime();
cstmt.execute();
long stopTime = clock.getTime();
String amRMAddress = cstmt.getString(2);
String clientRMAddress = cstmt.getString(3);
String rmAdminAddress = cstmt.getString(4);
String webAppAddress = cstmt.getString(5);
if((amRMAddress == null) || (clientRMAddress == null)) {


log.info("the queried subcluster does not exist");
}
}

};