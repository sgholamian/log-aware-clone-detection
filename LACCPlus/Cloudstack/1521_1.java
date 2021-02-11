//,temp,ClusterMO.java,232,243,temp,ClusterMO.java,219,230
//,2
public class xxx {
    @Override
    public VirtualMachineMO findVmOnPeerHyperHost(String name) throws Exception {
        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }

        String instanceNameCustomField = "value[" + key + "]";

        ObjectContent[] ocs = getVmPropertiesOnHyperHost(new String[] {"name", instanceNameCustomField});
        return HypervisorHostHelper.findVmFromObjectContent(_context, ocs, name, instanceNameCustomField);
    }

};