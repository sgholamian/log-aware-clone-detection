//,temp,UsageIPAddressDaoImpl.java,84,149,temp,UsageVMSnapshotDaoImpl.java,69,119
//,3
public class xxx {
    @Override
    public List<UsageVMSnapshotVO> getUsageRecords(Long accountId, Long domainId, Date startDate, Date endDate) {
        List<UsageVMSnapshotVO> usageRecords = new ArrayList<UsageVMSnapshotVO>();

        String sql = GET_USAGE_RECORDS_BY_ACCOUNT;
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;

        try {
            int i = 1;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(i++, accountId);
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //id, zone_id, account_id, domain_iVMSnapshotVOd, vm_id, disk_offering_id, size, created, processed
                Long vId = Long.valueOf(rs.getLong(1));
                Long zoneId = Long.valueOf(rs.getLong(2));
                Long acctId = Long.valueOf(rs.getLong(3));
                Long dId = Long.valueOf(rs.getLong(4));
                Long vmId = Long.valueOf(rs.getLong(5));
                Long doId = Long.valueOf(rs.getLong(6));
                if (doId == 0) {
                    doId = null;
                }
                Long size = Long.valueOf(rs.getLong(7));
                Date createdDate = null;
                Date processDate = null;
                String createdTS = rs.getString(8);
                String processed = rs.getString(9);

                if (createdTS != null) {
                    createdDate = DateUtil.parseDateString(s_gmtTimeZone, createdTS);
                }
                if (processed != null) {
                    processDate = DateUtil.parseDateString(s_gmtTimeZone, processed);
                }
                usageRecords.add(new UsageVMSnapshotVO(vId, zoneId, acctId, dId, vmId, doId, size, createdDate, processDate));
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