//,temp,UsageSanityChecker.java,184,196,temp,Upgrade224to225.java,339,353
//,3
public class xxx {
    protected void readMaxId() throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("select max(id) from cloud_usage.cloud_usage");
             ResultSet rs = pstmt.executeQuery();)
        {
            maxId = -1;
            if (rs.next() && (rs.getInt(1) > 0)) {
                maxId = rs.getInt(1);
                lastCheckId += " and cu.id <= ?";
            }
        }catch (Exception e) {
            s_logger.error("readMaxId:"+e.getMessage(),e);
        }
    }

};