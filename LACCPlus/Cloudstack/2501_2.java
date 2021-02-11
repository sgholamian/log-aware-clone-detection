//,temp,Upgrade302to40.java,949,973,temp,Upgrade430to440.java,203,228
//,3
public class xxx {
    private void updateVlanUris(Connection conn) {
        s_logger.debug("updating vlan URIs");
        try(PreparedStatement selectstatement = conn.prepareStatement("SELECT id, vlan_id FROM `cloud`.`vlan` where vlan_id not like '%:%'");
            ResultSet results = selectstatement.executeQuery()) {

            while (results.next()) {
                long id = results.getLong(1);
                String vlan = results.getString(2);
                if (vlan == null || "".equals(vlan)) {
                    continue;
                }
                String vlanUri = BroadcastDomainType.Vlan.toUri(vlan).toString();
                try(PreparedStatement updatestatement = conn.prepareStatement("update `cloud`.`vlan` set vlan_id=? where id=?");)
                {
                    updatestatement.setString(1, vlanUri);
                    updatestatement.setLong(2, id);
                    updatestatement.executeUpdate();
                } catch (SQLException e) {
                    throw new CloudRuntimeException("Unable to update vlan URI " + vlanUri + " for vlan record " + id, e);
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update vlan URIs ", e);
        }
        s_logger.debug("Done updateing vlan URIs");
    }

};