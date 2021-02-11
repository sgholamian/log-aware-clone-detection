//,temp,Upgrade410to420.java,2163,2202,temp,Upgrade410to420.java,2042,2089
//,3
public class xxx {
    private void migrateTemplateSwiftRef(Connection conn, Map<Long, Long> swiftStoreMap) {
        s_logger.debug("Updating template_store_ref table from template_swift_ref table");
        try (
                PreparedStatement tmplStoreInsert =
                    conn.prepareStatement("INSERT INTO `cloud`.`template_store_ref` (store_id,  template_id, created, download_pct, size, physical_size, download_state, local_path, install_path, update_count, ref_cnt, store_role, state) values(?, ?, ?, 100, ?, ?, 'DOWNLOADED', '?', '?', 0, 0, 'Image', 'Ready')");
                PreparedStatement s3Query = conn.prepareStatement("select swift_id, template_id, created, path, size, physical_size from `cloud`.`template_swift_ref`");
                ResultSet rs = s3Query.executeQuery();
            ) {
            while (rs.next()) {
                Long swift_id = rs.getLong("swift_id");
                Long tmpl_id = rs.getLong("template_id");
                Date created = rs.getDate("created");
                String path = rs.getString("path");
                Long size = rs.getObject("size") != null ? rs.getLong("size") : null;
                Long psize = rs.getObject("physical_size") != null ? rs.getLong("physical_size") : null;

                tmplStoreInsert.setLong(1, swiftStoreMap.get(swift_id));
                tmplStoreInsert.setLong(2, tmpl_id);
                tmplStoreInsert.setDate(3, created);
                if (size != null) {
                    tmplStoreInsert.setLong(4, size);
                } else {
                    tmplStoreInsert.setNull(4, Types.BIGINT);
                }
                if (psize != null) {
                    tmplStoreInsert.setLong(5, psize);
                } else {
                    tmplStoreInsert.setNull(5, Types.BIGINT);
                }
                tmplStoreInsert.setString(6, path);
                tmplStoreInsert.setString(7, path);
                tmplStoreInsert.executeUpdate();
            }
        } catch (SQLException e) {
            String msg = "Unable to migrate template_swift_ref." + e.getMessage();
            s_logger.error(msg);
            throw new CloudRuntimeException(msg, e);
        }
        s_logger.debug("Completed migrating template_swift_ref table.");
    }

};