//,temp,sample_3593.java,2,11,temp,sample_3916.java,2,11
//,2
public class xxx {
public void testShowRoleGrant() throws SQLException {
Statement stmt = con.createStatement();
try {
stmt.execute("drop role role1");
} catch (Exception ex) {


log.info("ignoring error during drop role");
}
}

};