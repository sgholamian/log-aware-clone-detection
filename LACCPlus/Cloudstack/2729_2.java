//,temp,UsageLoadBalancerPolicyDaoImpl.java,99,168,temp,UsageStorageDaoImpl.java,146,219
//,3
public class xxx {
    @Override
    public List<UsageStorageVO> getUsageRecords(Long accountId, Long domainId, Date startDate, Date endDate, boolean limit, int page) {
        List<UsageStorageVO> usageRecords = new ArrayList<UsageStorageVO>();

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
                    //id, zone_id, account_id, domain_id, storage_type, size, created, deleted
                    Long id = Long.valueOf(rs.getLong(1));
                    Long zoneId = Long.valueOf(rs.getLong(2));
                    Long acctId = Long.valueOf(rs.getLong(3));
                    Long dId = Long.valueOf(rs.getLong(4));
                    Integer type = Integer.valueOf(rs.getInt(5));
                    Long sourceId = Long.valueOf(rs.getLong(6));
                    Long size = Long.valueOf(rs.getLong(7));
                    Long virtualSize = Long.valueOf(rs.getLong(10));
                    Date createdDate = null;
                    Date deletedDate = null;
                    String createdTS = rs.getString(8);
                    String deletedTS = rs.getString(9);

                    if (createdTS != null) {
                        createdDate = DateUtil.parseDateString(s_gmtTimeZone, createdTS);
                    }
                    if (deletedTS != null) {
                        deletedDate = DateUtil.parseDateString(s_gmtTimeZone, deletedTS);
                    }

                    usageRecords.add(new UsageStorageVO(id, zoneId, acctId, dId, type, sourceId, size, virtualSize, createdDate, deletedDate));
                }
            }catch(SQLException e)
            {
                throw new CloudException("getUsageRecords:"+e.getMessage(),e);
            }
        }catch (Exception e) {
            txn.rollback();
            s_logger.error("getUsageRecords:Exception:"+e.getMessage(), e);
        } finally {
            txn.close();
        }
        return usageRecords;
    }

};