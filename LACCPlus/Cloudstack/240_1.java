//,temp,Upgrade2214to30.java,757,775,temp,Upgrade410to420.java,810,821
//,3
public class xxx {
    protected void updateRouters(Connection conn) {
        PreparedStatement pstmt = null;
        try {
            s_logger.debug("Updating domain_router table");
            pstmt =
                conn.prepareStatement("UPDATE domain_router, virtual_router_providers vrp LEFT JOIN (physical_network_service_providers pnsp INNER JOIN physical_network pntwk INNER JOIN vm_instance vm INNER JOIN domain_router vr) ON (vrp.nsp_id = pnsp.id AND pnsp.physical_network_id = pntwk.id AND pntwk.data_center_id = vm.data_center_id AND vm.id=vr.id) SET vr.element_id=vrp.id;");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update router table. ", e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new CloudRuntimeException("Unable to close statement for router table. ", e);
            }
        }
    }

};