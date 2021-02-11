//,temp,NexentaStorAppliance.java,324,334,temp,NexentaStorAppliance.java,141,150
//,3
public class xxx {
    void addLuMappingEntry(String luName, String targetGroupName) {
        MappingEntry mappingEntry = new MappingEntry(targetGroupName, "0");
        try {
            client.execute(AddMappingEntryNmsResponse.class, "scsidisk", "add_lun_mapping_entry", luName, mappingEntry);
        } catch (CloudRuntimeException ex) {
            if (!ex.getMessage().contains("view already exists")) {
                throw ex;
            }
            logger.debug("Ignored LU mapping entry addition error " + ex);
        }
    }

};