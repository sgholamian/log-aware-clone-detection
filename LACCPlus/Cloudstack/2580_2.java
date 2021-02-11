//,temp,Upgrade410to420.java,2418,2457,temp,Upgrade410to420.java,641,690
//,3
public class xxx {
    private void upgradeVmwareLabels(Connection conn) {
        String newLabel;
        String trafficType = null;
        String trafficTypeVswitchParam;
        String trafficTypeVswitchParamValue;

        try (PreparedStatement pstmt =
                     conn.prepareStatement("select name,value from `cloud`.`configuration` where category='Hidden' and value is not NULL and name REGEXP 'vmware*.vswitch';");)
        {
            // update the existing vmware traffic labels
            try(ResultSet rsParams = pstmt.executeQuery();) {
                while (rsParams.next()) {
                    trafficTypeVswitchParam = rsParams.getString("name");
                    trafficTypeVswitchParamValue = rsParams.getString("value");
                    // When upgraded from 4.0 to 4.1 update physical network traffic label with trafficTypeVswitchParam
                    if (trafficTypeVswitchParam.equals("vmware.private.vswitch")) {
                        trafficType = "Management"; //TODO(sateesh): Ignore storage traffic, as required physical network already implemented, anything else tobe done?
                    } else if (trafficTypeVswitchParam.equals("vmware.public.vswitch")) {
                        trafficType = "Public";
                    } else if (trafficTypeVswitchParam.equals("vmware.guest.vswitch")) {
                        trafficType = "Guest";
                    }
                    try(PreparedStatement sel_pstmt =
                            conn.prepareStatement("select physical_network_id, traffic_type, vmware_network_label from physical_network_traffic_types where vmware_network_label is not NULL and traffic_type=?;");) {
                        pstmt.setString(1, trafficType);
                        try(ResultSet rsLabel = sel_pstmt.executeQuery();) {
                            newLabel = getNewLabel(rsLabel, trafficTypeVswitchParamValue);
                            try(PreparedStatement update_pstmt =
                                    conn.prepareStatement("update physical_network_traffic_types set vmware_network_label = ? where traffic_type = ? and vmware_network_label is not NULL;");) {
                                s_logger.debug("Updating vmware label for " + trafficType + " traffic. Update SQL statement is " + pstmt);
                                pstmt.setString(1, newLabel);
                                pstmt.setString(2, trafficType);
                                update_pstmt.executeUpdate();
                            }catch (SQLException e) {
                                throw new CloudRuntimeException("Unable to set vmware traffic labels ", e);
                            }
                        }catch (SQLException e) {
                            throw new CloudRuntimeException("Unable to set vmware traffic labels ", e);
                        }
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("Unable to set vmware traffic labels ", e);
                    }
                }
            }catch (SQLException e) {
                throw new CloudRuntimeException("Unable to set vmware traffic labels ", e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to set vmware traffic labels ", e);
        }
    }

};