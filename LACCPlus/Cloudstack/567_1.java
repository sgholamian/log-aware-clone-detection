//,temp,UsageVMSnapshotOnPrimaryDaoImpl.java,66,115,temp,UsageStorageDaoImpl.java,146,219
//,3
public class xxx {
    @Override
    public List<UsageSnapshotOnPrimaryVO> getUsageRecords(Long accountId, Long domainId, Date startDate, Date endDate) {
        List<UsageSnapshotOnPrimaryVO> usageRecords = new ArrayList<UsageSnapshotOnPrimaryVO>();

        String sql = GET_USAGE_RECORDS_BY_ACCOUNT;
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;

        try {
            int i = 1;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(i++, accountId);
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            s_logger.debug("GET_USAGE_RECORDS_BY_ACCOUNT " + pstmt);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //id, zone_id, account_id, domain_iVMSnapshotVOd, vm_id, disk_offering_id, size, created, deleted
                Long vId = Long.valueOf(rs.getLong(1));
                Long zoneId = Long.valueOf(rs.getLong(2));
                Long acctId = Long.valueOf(rs.getLong(3));
                Long dId = Long.valueOf(rs.getLong(4));
                Long vmId = Long.valueOf(rs.getLong(5));
                String name = String.valueOf(rs.getString(6));
                Integer type = Integer.valueOf(rs.getInt(7));
                Long physicalSize = Long.valueOf(rs.getLong(8));
                Long virtaulSize = Long.valueOf(rs.getLong(9));
                Date createdDate = null;
                Date deleteDate = null;
                String createdTS = rs.getString(10);
                String deleted = rs.getString(11);

                if (createdTS != null) {
                    createdDate = DateUtil.parseDateString(s_gmtTimeZone, createdTS);
                }
                if (deleted != null) {
                    deleteDate = DateUtil.parseDateString(s_gmtTimeZone, deleted);
                }
                usageRecords.add(new UsageSnapshotOnPrimaryVO(vId, zoneId, acctId, dId, vmId, name, type, virtaulSize, physicalSize, createdDate, deleteDate));
            }
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error getting usage records", e);
        } finally {
            txn.close();
        }

        return usageRecords;
    }

};