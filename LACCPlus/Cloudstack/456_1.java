//,temp,UsageSanityChecker.java,96,121,temp,Merovingian2.java,209,226
//,3
public class xxx {
    protected void checkMaxUsage() throws SQLException {
        int aggregationRange = DEFAULT_AGGREGATION_RANGE;
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT value FROM `cloud`.`configuration` where name = 'usage.stats.job.aggregation.range'");)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
               if (rs.next()) {
                    aggregationRange = rs.getInt(1);
                } else {
                    s_logger.debug("Failed to retrieve aggregation range. Using default : " + aggregationRange);
                }
            }catch (SQLException e) {
                s_logger.error("checkMaxUsage:Exception:"+e.getMessage());
                throw new CloudRuntimeException("checkMaxUsage:Exception:"+e.getMessage());
            }
        } catch (SQLException e) {
            s_logger.error("checkMaxUsage:Exception:"+e.getMessage());
            throw new CloudRuntimeException("checkMaxUsage:Exception:"+e.getMessage());
        }
        int aggregationHours = aggregationRange / 60;

        addCheckCase("SELECT count(*) FROM `cloud_usage`.`cloud_usage` cu where usage_type not in (4,5) and raw_usage > "
                + aggregationHours,
                "usage records with raw_usage > " + aggregationHours,
                lastCheckId);
    }

};