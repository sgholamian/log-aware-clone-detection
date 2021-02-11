//,temp,DatabaseAccessObject.java,47,54,temp,Upgrade410to420.java,2408,2416
//,3
public class xxx {
    protected void setRAWformatForRBDVolumes(Connection conn) {
        try(PreparedStatement pstmt = conn.prepareStatement("UPDATE volumes SET format = 'RAW' WHERE pool_id IN(SELECT id FROM storage_pool WHERE pool_type = 'RBD')");)
        {
            s_logger.debug("Setting format to RAW for all volumes on RBD primary storage pools");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to update volume format to RAW for volumes on RBD pools due to exception ", e);
        }
    }

};