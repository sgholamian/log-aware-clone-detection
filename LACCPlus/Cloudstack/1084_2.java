//,temp,DatacenterMO.java,84,92,temp,ClusterMO.java,219,230
//,3
public class xxx {
    @Override
    public VirtualMachineMO findVmOnHyperHost(String name) throws Exception {

        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }

        String instanceNameCustomField = "value[" + key + "]";
        ObjectContent[] ocs = getVmPropertiesOnHyperHost(new String[] {"name", instanceNameCustomField});
        return HypervisorHostHelper.findVmFromObjectContent(_context, ocs, name, instanceNameCustomField);
    }

};