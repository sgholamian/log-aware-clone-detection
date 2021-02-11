//,temp,SecondaryStorageVmDaoImpl.java,235,270,temp,SecondaryStorageVmDaoImpl.java,165,199
//,3
public class xxx {
    @Override
    public List<Long> listRunningSecStorageOrderByLoad(SecondaryStorageVm.Role role, long zoneId) {

        List<Long> l = new ArrayList<Long>();
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            String sql;
            if (role == null) {
                sql =
                    "SELECT s.id, count(l.id) as count FROM secondary_storage_vm s INNER JOIN vm_instance v ON s.id=v.id LEFT JOIN cmd_exec_log l ON s.id=l.instance_id WHERE v.state='Running' AND v.data_center_id=? GROUP BY s.id ORDER BY count";
            } else {
                sql =
                    "SELECT s.id, count(l.id) as count FROM secondary_storage_vm s INNER JOIN vm_instance v ON s.id=v.id LEFT JOIN cmd_exec_log l ON s.id=l.instance_id WHERE v.state='Running' AND v.data_center_id=? AND s.role=? GROUP BY s.id ORDER BY count";
            }

            pstmt = txn.prepareAutoCloseStatement(sql);

            if (role == null) {
                pstmt.setLong(1, zoneId);
            } else {
                pstmt.setLong(1, zoneId);
                pstmt.setString(2, role.toString());
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            s_logger.error("Unexpected exception ", e);
        }

        return l;
    }

};