//,temp,SecondaryStorageVmDaoImpl.java,235,270,temp,SecondaryStorageVmDaoImpl.java,165,199
//,3
public class xxx {
    @Override
    public List<Long> getRunningSecStorageVmListByMsid(SecondaryStorageVm.Role role, long msid) {
        List<Long> l = new ArrayList<Long>();
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        ;
        PreparedStatement pstmt = null;
        try {
            String sql;
            if (role == null) {
                sql =
                    "SELECT s.id FROM secondary_storage_vm s, vm_instance v, host h " + "WHERE s.id=v.id AND v.state='Running' AND v.host_id=h.id AND h.mgmt_server_id=?";
            } else {
                sql =
                    "SELECT s.id FROM secondary_storage_vm s, vm_instance v, host h "
                        + "WHERE s.id=v.id AND v.state='Running' AND s.role=? AND v.host_id=h.id AND h.mgmt_server_id=?";
            }

            pstmt = txn.prepareAutoCloseStatement(sql);

            if (role == null) {
                pstmt.setLong(1, msid);
            } else {
                pstmt.setString(1, role.toString());
                pstmt.setLong(2, msid);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                l.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            s_logger.debug("Caught SQLException: ", e);
        }
        return l;
    }

};