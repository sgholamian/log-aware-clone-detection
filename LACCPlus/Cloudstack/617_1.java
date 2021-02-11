//,temp,UsageIPAddressDaoImpl.java,84,149,temp,UsageSecurityGroupDaoImpl.java,83,150
//,3
public class xxx {
    @Override
    public List<UsageIPAddressVO> getUsageRecords(Long accountId, Long domainId, Date startDate, Date endDate) {
        List<UsageIPAddressVO> usageRecords = new ArrayList<UsageIPAddressVO>();

        Long param1 = null;
        String sql = null;
        if (accountId != null) {
            sql = GET_USAGE_RECORDS_BY_ACCOUNT;
            param1 = accountId;
        } else if (domainId != null) {
            sql = GET_USAGE_RECORDS_BY_DOMAIN;
            param1 = domainId;
        } else {
            sql = GET_ALL_USAGE_RECORDS;
        }

        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        PreparedStatement pstmt = null;

        try {
            int i = 1;
            pstmt = txn.prepareAutoCloseStatement(sql);
            if (param1 != null) {
                pstmt.setLong(i++, param1);
            }
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //account_id, domain_id, zone_id, address, assigned, released
                Long id = Long.valueOf(rs.getLong(1));
                Long acctId = Long.valueOf(rs.getLong(2));
                Long dId = Long.valueOf(rs.getLong(3));
                Long zId = Long.valueOf(rs.getLong(4));
                String addr = rs.getString(5);
                Boolean isSourceNat = Boolean.valueOf(rs.getBoolean(6));
                Boolean isSystem = Boolean.valueOf(rs.getBoolean(7));
                Date assignedDate = null;
                Date releasedDate = null;
                String assignedTS = rs.getString(8);
                String releasedTS = rs.getString(9);

                if (assignedTS != null) {
                    assignedDate = DateUtil.parseDateString(s_gmtTimeZone, assignedTS);
                }
                if (releasedTS != null) {
                    releasedDate = DateUtil.parseDateString(s_gmtTimeZone, releasedTS);
                }

                usageRecords.add(new UsageIPAddressVO(id, acctId, dId, zId, addr, isSourceNat, isSystem, assignedDate, releasedDate));
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