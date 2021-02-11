//,temp,DatabaseConfig.java,983,1046,temp,DatabaseConfig.java,917,981
//,3
public class xxx {
    @DB
    protected void saveDiskOffering() {
        long id = Long.parseLong(_currentObjectParams.get("id"));
        long domainId = Long.parseLong(_currentObjectParams.get("domainId"));
        String name = _currentObjectParams.get("name");
        String displayText = _currentObjectParams.get("displayText");
        ProvisioningType provisioningType = ProvisioningType.valueOf(_currentObjectParams.get("provisioningtype"));
        long diskSpace = Long.parseLong(_currentObjectParams.get("diskSpace"));
        diskSpace = diskSpace * 1024 * 1024;
//        boolean mirroring = Boolean.parseBoolean(_currentObjectParams.get("mirrored"));
        String tags = _currentObjectParams.get("tags");
        String useLocal = _currentObjectParams.get("useLocal");
        boolean local = false;
        if (useLocal != null) {
            local = Boolean.parseBoolean(useLocal);
        }

        if (tags != null && tags.length() > 0) {
            String[] tokens = tags.split(",");
            StringBuilder newTags = new StringBuilder();
            for (String token : tokens) {
                newTags.append(token.trim()).append(",");
            }
            newTags.delete(newTags.length() - 1, newTags.length());
            tags = newTags.toString();
        }
        DiskOfferingVO diskOffering = new DiskOfferingVO(domainId, name, displayText, provisioningType, diskSpace, tags, false, null, null, null);
        diskOffering.setUseLocalStorage(local);

        Long bytesReadRate = Long.parseLong(_currentObjectParams.get("bytesReadRate"));
        if (bytesReadRate != null && (bytesReadRate > 0))
            diskOffering.setBytesReadRate(bytesReadRate);
        Long bytesWriteRate = Long.parseLong(_currentObjectParams.get("bytesWriteRate"));
        if (bytesWriteRate != null && (bytesWriteRate > 0))
            diskOffering.setBytesWriteRate(bytesWriteRate);
        Long iopsReadRate = Long.parseLong(_currentObjectParams.get("iopsReadRate"));
        if (iopsReadRate != null && (iopsReadRate > 0))
            diskOffering.setIopsReadRate(iopsReadRate);
        Long iopsWriteRate = Long.parseLong(_currentObjectParams.get("iopsWriteRate"));
        if (iopsWriteRate != null && (iopsWriteRate > 0))
            diskOffering.setIopsWriteRate(iopsWriteRate);

        DiskOfferingDaoImpl offering = ComponentContext.inject(DiskOfferingDaoImpl.class);
        try {
            offering.persist(diskOffering);
        } catch (Exception e) {
            s_logger.error("error creating disk offering", e);

        }
        /*
        String insertSql = "INSERT INTO `cloud`.`disk_offering` (id, domain_id, name, display_text, disk_size, mirrored, tags) " +
                "VALUES (" + id + "," + domainId + ",'" + name + "','" + displayText + "'," + diskSpace + "," + mirroring + ", ? )";

        Transaction txn = Transaction.currentTxn();
        try {
            PreparedStatement stmt = txn.prepareAutoCloseStatement(insertSql);
            stmt.setString(1, tags);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            s_logger.error("error creating disk offering", ex);
            return;
        }
         */
    }

};