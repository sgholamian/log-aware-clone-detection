//,temp,Upgrade227to228Premium.java,84,121,temp,Upgrade227to228.java,59,88
//,3
public class xxx {
    @Override
    public void performDataMigration(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("select id from data_center");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long dcId = rs.getLong(1);
                pstmt = conn.prepareStatement("select id from host where data_center_id=? and type='SecondaryStorage'");
                pstmt.setLong(1, dcId);
                ResultSet rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    long secHostId = rs1.getLong(1);
                    pstmt = conn.prepareStatement("update snapshots set sechost_id=? where data_center_id=?");
                    pstmt.setLong(1, secHostId);
                    pstmt.setLong(2, dcId);
                    pstmt.executeUpdate();
                }
            }

            pstmt = conn.prepareStatement("update disk_offering set disk_size = disk_size * 1024 * 1024 where disk_size <= 2 * 1024 * 1024 and disk_size != 0");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            s_logger.error("Failed to DB migration for multiple secondary storages", e);
            throw new CloudRuntimeException("Failed to DB migration for multiple secondary storages", e);
        }

        updateDomainLevelNetworks(conn);
        updateVolumeUsageRecords(conn);
    }

};