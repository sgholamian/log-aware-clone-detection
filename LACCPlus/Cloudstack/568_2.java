//,temp,DatabaseConfig.java,983,1046,temp,DatabaseConfig.java,917,981
//,3
public class xxx {
    @DB
    protected void saveServiceOffering() {
        long id = Long.parseLong(_currentObjectParams.get("id"));
        String name = _currentObjectParams.get("name");
        String displayText = _currentObjectParams.get("displayText");
        ProvisioningType provisioningType = ProvisioningType.valueOf(_currentObjectParams.get("provisioningType"));
        int cpu = Integer.parseInt(_currentObjectParams.get("cpu"));
        int ramSize = Integer.parseInt(_currentObjectParams.get("ramSize"));
        int speed = Integer.parseInt(_currentObjectParams.get("speed"));
        String useLocalStorageValue = _currentObjectParams.get("useLocalStorage");

//        int nwRate = Integer.parseInt(_currentObjectParams.get("nwRate"));
//        int mcRate = Integer.parseInt(_currentObjectParams.get("mcRate"));
        boolean ha = Boolean.parseBoolean(_currentObjectParams.get("enableHA"));
        boolean mirroring = Boolean.parseBoolean(_currentObjectParams.get("mirrored"));

        boolean useLocalStorage;
        if (useLocalStorageValue != null) {
            if (Boolean.parseBoolean(useLocalStorageValue)) {
                useLocalStorage = true;
            } else {
                useLocalStorage = false;
            }
        } else {
            useLocalStorage = false;
        }

        ServiceOfferingVO serviceOffering =
            new ServiceOfferingVO(name, cpu, ramSize, speed, null, null, ha, displayText,
                    provisioningType, useLocalStorage, false, null, false, null, false);

        Long bytesReadRate = Long.parseLong(_currentObjectParams.get("bytesReadRate"));
        if ((bytesReadRate != null) && (bytesReadRate > 0))
            serviceOffering.setBytesReadRate(bytesReadRate);
        Long bytesWriteRate = Long.parseLong(_currentObjectParams.get("bytesWriteRate"));
        if ((bytesWriteRate != null) && (bytesWriteRate > 0))
            serviceOffering.setBytesWriteRate(bytesWriteRate);
        Long iopsReadRate = Long.parseLong(_currentObjectParams.get("iopsReadRate"));
        if ((iopsReadRate != null) && (iopsReadRate > 0))
            serviceOffering.setIopsReadRate(iopsReadRate);
        Long iopsWriteRate = Long.parseLong(_currentObjectParams.get("iopsWriteRate"));
        if ((iopsWriteRate != null) && (iopsWriteRate > 0))
            serviceOffering.setIopsWriteRate(iopsWriteRate);

        ServiceOfferingDaoImpl dao = ComponentContext.inject(ServiceOfferingDaoImpl.class);
        try {
            dao.persist(serviceOffering);
        } catch (Exception e) {
            s_logger.error("error creating service offering", e);

        }
        /*
        String insertSql = "INSERT INTO `cloud`.`service_offering` (id, name, cpu, ram_size, speed, nw_rate, mc_rate, created, ha_enabled, mirrored, display_text, guest_ip_type, use_local_storage) " +
                "VALUES (" + id + ",'" + name + "'," + cpu + "," + ramSize + "," + speed + "," + nwRate + "," + mcRate + ",now()," + ha + "," + mirroring + ",'" + displayText + "','" + guestIpType + "','" + useLocalStorage + "')";

        Transaction txn = Transaction.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(insertSql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            s_logger.error("error creating service offering", ex);
            return;
        }
         */
    }

};