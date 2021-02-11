//,temp,sample_1856.java,2,17,temp,sample_3734.java,2,17
//,2
public class xxx {
public void dummy_method(){
pstmt.setLong(1, datacenterId);
pstmt.setLong(2, podId);
pstmt.setLong(3, templateId);
pstmt.setString(4, downloadState.toString());
try(ResultSet rs = pstmt.executeQuery();) {
while (rs.next()) {
long id = rs.getLong(1);
result.add(findById(id));
}
}catch (SQLException e) {


log.info("listbytemplatestatus exception");
}
}

};