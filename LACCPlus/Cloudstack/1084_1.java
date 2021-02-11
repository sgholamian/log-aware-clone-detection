//,temp,DatacenterMO.java,84,92,temp,ClusterMO.java,219,230
//,3
public class xxx {
    public VirtualMachineMO findVm(String vmName) throws Exception {
        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }
        String instanceNameCustomField = "value[" + key + "]";
        List<ObjectContent> ocs = getVmPropertiesOnDatacenterVmFolder(new String[] {"name", instanceNameCustomField});
        return HypervisorHostHelper.findVmFromObjectContent(_context, ocs.toArray(new ObjectContent[0]), vmName, instanceNameCustomField);
    }

};