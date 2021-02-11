//,temp,sample_9395.java,2,18,temp,sample_9394.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (rs != null) {
try {
rs.close();
} catch (SQLException e) {
}
}
if (pstmt != null) {
try {
pstmt.close();
} catch (SQLException e) {


log.info("ignored");
}
}
}

};