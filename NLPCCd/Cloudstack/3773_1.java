//,temp,sample_9394.java,2,18,temp,sample_9386.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (pstmtUpdate != null) {
try {
pstmtUpdate.close();
} catch (SQLException e) {
}
}
if (rs != null) {
try {
rs.close();
} catch (SQLException e) {


log.info("ignored");
}
}
}

};