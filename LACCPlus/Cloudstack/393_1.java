//,temp,Upgrade410to420.java,2418,2457,temp,Upgrade410to420.java,1365,1396
//,3
public class xxx {
    private void upgradeVpcServiceMap(Connection conn) {
        s_logger.debug("Upgrading VPC service Map");
        try(PreparedStatement listVpc = conn.prepareStatement("SELECT id, vpc_offering_id FROM `cloud`.`vpc` where removed is NULL");)
        {
            //Get all vpc Ids along with vpc offering Id
            try(ResultSet rs = listVpc.executeQuery();) {
                while (rs.next()) {
                    long vpc_id = rs.getLong(1);
                    long offering_id = rs.getLong(2);
                    //list all services and providers in offering
                    try(PreparedStatement listServiceProviders = conn.prepareStatement("SELECT service, provider FROM `cloud`.`vpc_offering_service_map` where vpc_offering_id = ?");) {
                        listServiceProviders.setLong(1, offering_id);
                        try(ResultSet rs1 = listServiceProviders.executeQuery();) {
                            //Insert entries in vpc_service_map
                            while (rs1.next()) {
                                String service = rs1.getString(1);
                                String provider = rs1.getString(2);
                                try (PreparedStatement insertProviders =
                                             conn.prepareStatement("INSERT INTO `cloud`.`vpc_service_map` (`vpc_id`, `service`, `provider`, `created`) VALUES (?, ?, ?, now());");) {
                                    insertProviders.setLong(1, vpc_id);
                                    insertProviders.setString(2, service);
                                    insertProviders.setString(3, provider);
                                    insertProviders.executeUpdate();
                                } catch (SQLException e) {
                                    throw new CloudRuntimeException("Error during VPC service map upgrade", e);
                                }
                            }
                        }catch (SQLException e) {
                            throw new CloudRuntimeException("Error during VPC service map upgrade", e);
                        }
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("Error during VPC service map upgrade", e);
                    }
                    s_logger.debug("Upgraded service map for VPC: " + vpc_id);
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Error during VPC service map upgrade", e);
        }
    }

};