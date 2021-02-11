//,temp,UsageSecurityGroupDaoImpl.java,83,150,temp,UsageNetworkOfferingDaoImpl.java,83,155
//,3
public class xxx {
    @Override
    public List<UsageSecurityGroupVO> getUsageRecords(Long accountId, Long domainId, Date startDate, Date endDate, boolean limit, int page) {
        List<UsageSecurityGroupVO> usageRecords = new ArrayList<UsageSecurityGroupVO>();

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

        if (limit) {
            int startIndex = 0;
            if (page > 0) {
                startIndex = 500 * (page - 1);
            }
            sql += " LIMIT " + startIndex + ",500";
        }

        TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.USAGE_DB);
        int i = 1;
        try (PreparedStatement pstmt = txn.prepareStatement(sql);){
            if (param1 != null) {
                pstmt.setLong(i++, param1);
            }
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), startDate));
            pstmt.setString(i++, DateUtil.getDateDisplayString(TimeZone.getTimeZone("GMT"), endDate));
            try(ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    //zoneId, account_id, domain_id, vm_instance_id, security_group_id, created, deleted
                    Long zoneId = Long.valueOf(rs.getLong(1));
                    Long acctId = Long.valueOf(rs.getLong(2));
                    Long dId = Long.valueOf(rs.getLong(3));
                    long vmId = Long.valueOf(rs.getLong(4));
                    long sgId = Long.valueOf(rs.getLong(5));
                    Date createdDate = null;
                    Date deletedDate = null;
                    String createdTS = rs.getString(6);
                    String deletedTS = rs.getString(7);

                    if (createdTS != null) {
                        createdDate = DateUtil.parseDateString(s_gmtTimeZone, createdTS);
                    }
                    if (deletedTS != null) {
                        deletedDate = DateUtil.parseDateString(s_gmtTimeZone, deletedTS);
                    }
                    usageRecords.add(new UsageSecurityGroupVO(zoneId, acctId, dId, vmId, sgId, createdDate, deletedDate));
                }
            }catch (SQLException e) {
                throw new CloudException("Error getting usage records"+e.getMessage(), e);
            }
        } catch (Exception e) {
            txn.rollback();
            s_logger.warn("Error getting usage records:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
        return usageRecords;
    }

};