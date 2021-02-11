//,temp,UsageVMInstanceDaoImpl.java,92,149,temp,UsageStorageDaoImpl.java,146,219
//,3
public class xxx {
    @Override
    public List<UsageVMInstanceVO> getUsageRecords(long accountId, Date startDate, Date endDate) {
        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;
        List<UsageVMInstanceVO> usageInstances = new ArrayList<UsageVMInstanceVO>();
        try {
            String sql = GET_USAGE_RECORDS_BY_ACCOUNT;
            pstmt = txn.prepareAutoCloseStatement(sql);
            pstmt.setLong(1, accountId);
            pstmt.setString(2, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(3, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(4, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(5, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(6, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(7, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int r_usageType = rs.getInt(1);
                long r_zoneId = rs.getLong(2);
                long r_accountId = rs.getLong(3);
                long r_vmId = rs.getLong(4);
                String r_vmName = rs.getString(5);
                Long r_cpuSpeed = rs.getLong(6);
                if (rs.wasNull()) {
                    r_cpuSpeed = null;
                }
                Long r_cpuCores = rs.getLong(7);
                if (rs.wasNull()) {
                    r_cpuCores = null;
                }
                Long r_memory = rs.getLong(8);
                if (rs.wasNull()) {
                    r_memory = null;
                }
                long r_soId = rs.getLong(9);
                long r_tId = rs.getLong(10);
                String hypervisorType = rs.getString(11);
                String r_startDate = rs.getString(12);
                String r_endDate = rs.getString(13);
                Date instanceStartDate = null;
                Date instanceEndDate = null;
                if (r_startDate != null) {
                    instanceStartDate = DateUtil.parseDateString(s_gmtTimeZone, r_startDate);
                }
                if (r_endDate != null) {
                    instanceEndDate = DateUtil.parseDateString(s_gmtTimeZone, r_endDate);
                }
                UsageVMInstanceVO usageInstance =
                    new UsageVMInstanceVO(r_usageType, r_zoneId, r_accountId, r_vmId, r_vmName, r_soId, r_tId, r_cpuSpeed, r_cpuCores, r_memory, hypervisorType, instanceStartDate, instanceEndDate);
                usageInstances.add(usageInstance);
            }
        } catch (Exception ex) {
            s_logger.error("error retrieving usage vm instances for account id: " + accountId, ex);
        } finally {
            txn.close();
        }
        return usageInstances;
    }

};