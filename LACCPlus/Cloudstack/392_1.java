//,temp,Upgrade410to420.java,2380,2406,temp,Merovingian2.java,209,226
//,3
public class xxx {
    private void migrateDatafromIsoIdInVolumesTable(Connection conn) {
      try(PreparedStatement pstmt = conn.prepareStatement("SELECT iso_id1 From `cloud`.`volumes`");)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    try(PreparedStatement alter_pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`volumes` DROP COLUMN `iso_id`");) {
                        alter_pstmt.executeUpdate();
                        try(PreparedStatement alter_iso_pstmt =
                                conn.prepareStatement("ALTER TABLE `cloud`.`volumes` CHANGE COLUMN `iso_id1` `iso_id` bigint(20) unsigned COMMENT 'The id of the iso from which the volume was created'");) {
                            alter_iso_pstmt.executeUpdate();
                        }catch (SQLException e) {
                            s_logger.error("migrateDatafromIsoIdInVolumesTable:Exception:"+e.getMessage(),e);
                            //implies iso_id1 is not present, so do nothing.
                        }
                    }catch (SQLException e) {
                        s_logger.error("migrateDatafromIsoIdInVolumesTable:Exception:"+e.getMessage(),e);
                        //implies iso_id1 is not present, so do nothing.
                    }
                }
            }catch (SQLException e) {
                s_logger.error("migrateDatafromIsoIdInVolumesTable:Exception:"+e.getMessage(),e);
                //implies iso_id1 is not present, so do nothing.
            }
        } catch (SQLException e) {
            //implies iso_id1 is not present, so do nothing.
        }
    }

};