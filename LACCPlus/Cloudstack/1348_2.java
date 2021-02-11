//,temp,Upgrade410to420.java,2163,2202,temp,Upgrade410to420.java,1993,2039
//,3
public class xxx {
    private void migrateTemplateS3Ref(Connection conn, Map<Long, Long> s3StoreMap) {
        s_logger.debug("Updating template_store_ref table from template_s3_ref table");
        try(PreparedStatement tmplStoreInsert =
                    conn.prepareStatement("INSERT INTO `cloud`.`template_store_ref` (store_id,  template_id, created, download_pct, size, physical_size, download_state, local_path, install_path, update_count, ref_cnt, store_role, state) values(?, ?, ?, 100, ?, ?, 'DOWNLOADED', '?', '?', 0, 0, 'Image', 'Ready')");
        ) {
            try(PreparedStatement s3Query =
                    conn.prepareStatement("select template_s3_ref.s3_id, template_s3_ref.template_id, template_s3_ref.created, template_s3_ref.size, template_s3_ref.physical_size, vm_template.account_id from `cloud`.`template_s3_ref`, `cloud`.`vm_template` where vm_template.id = template_s3_ref.template_id");) {
                try(ResultSet rs = s3Query.executeQuery();) {
                    while (rs.next()) {
                        Long s3_id = rs.getLong("s3_id");
                        Long s3_tmpl_id = rs.getLong("template_id");
                        Date s3_created = rs.getDate("created");
                        Long s3_size = rs.getObject("size") != null ? rs.getLong("size") : null;
                        Long s3_psize = rs.getObject("physical_size") != null ? rs.getLong("physical_size") : null;
                        Long account_id = rs.getLong("account_id");
                        tmplStoreInsert.setLong(1, s3StoreMap.get(s3_id));
                        tmplStoreInsert.setLong(2, s3_tmpl_id);
                        tmplStoreInsert.setDate(3, s3_created);
                        if (s3_size != null) {
                            tmplStoreInsert.setLong(4, s3_size);
                        } else {
                            tmplStoreInsert.setNull(4, Types.BIGINT);
                        }
                        if (s3_psize != null) {
                            tmplStoreInsert.setLong(5, s3_psize);
                        } else {
                            tmplStoreInsert.setNull(5, Types.BIGINT);
                        }
                        String path = "template/tmpl/" + account_id + "/" + s3_tmpl_id;
                        tmplStoreInsert.setString(6, path);
                        tmplStoreInsert.setString(7, path);
                        tmplStoreInsert.executeUpdate();
                    }
                }catch (SQLException e) {
                    s_logger.error("Unable to migrate template_s3_ref." + e.getMessage(),e);
                    throw new CloudRuntimeException("Unable to migrate template_s3_ref." + e.getMessage(),e);
                }
            }catch (SQLException e) {
                s_logger.error("Unable to migrate template_s3_ref." + e.getMessage(),e);
                throw new CloudRuntimeException("Unable to migrate template_s3_ref." + e.getMessage(),e);
            }
        } catch (SQLException e) {
            s_logger.error("Unable to migrate template_s3_ref." + e.getMessage(),e);
            throw new CloudRuntimeException("Unable to migrate template_s3_ref." + e.getMessage(),e);
        }
        s_logger.debug("Completed migrating template_s3_ref table.");
    }

};