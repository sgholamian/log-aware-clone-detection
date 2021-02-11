//,temp,DatacenterMO.java,128,158,temp,HostMO.java,530,572
//,3
public class xxx {
    public VirtualMachineMO checkIfVmAlreadyExistsInVcenter(String vmNameOnVcenter, String vmNameInCS) throws Exception {
        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }

        List<ObjectContent> ocs = getVmPropertiesOnDatacenterVmFolder(new String[] {"name", String.format("value[%d]", key)});
        if (ocs != null && ocs.size() > 0) {
            for (ObjectContent oc : ocs) {
                List<DynamicProperty> props = oc.getPropSet();
                if (props != null) {
                    String vmVcenterName = null;
                    String vmInternalCSName = null;
                    for (DynamicProperty prop : props) {
                        if (prop.getName().equals("name")) {
                            vmVcenterName = prop.getVal().toString();
                        }
                        if (prop.getName().startsWith("value[") && prop.getVal() != null) {
                            vmInternalCSName = ((CustomFieldStringValue)prop.getVal()).getValue();
                        }
                    }
                    if (vmNameOnVcenter.equals(vmVcenterName)) {
                        if (vmInternalCSName != null && !vmInternalCSName.isEmpty() && !vmNameInCS.equals(vmInternalCSName)) {
                            return (new VirtualMachineMO(_context, oc.getObj()));
                        }
                    }
                }
            }
        }
        return null;
    }

};