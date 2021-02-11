//,temp,HostMO.java,530,572,temp,VmwareResource.java,5803,5853
//,3
public class xxx {
    private void loadVmCache() throws Exception {
        if (s_logger.isDebugEnabled())
            s_logger.debug("load VM cache on host");

        _vmCache.clear();

        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }

        // name is the name of the VM as it appears in vCenter. The CLOUD_VM_INTERNAL_NAME custom
        // field value contains the name of the VM as it is maintained internally by cloudstack (i-x-y).
        ObjectContent[] ocs = getVmPropertiesOnHyperHost(new String[] {"name", "value[" + key + "]"});
        if (ocs != null && ocs.length > 0) {
            for (ObjectContent oc : ocs) {
                List<DynamicProperty> props = oc.getPropSet();
                if (props != null) {
                    String vmVcenterName = null;
                    String vmInternalCSName = null;
                    for (DynamicProperty prop : props) {
                        if (prop.getName().equals("name")) {
                            vmVcenterName = prop.getVal().toString();
                        } else if (prop.getName().startsWith("value[")) {
                            if (prop.getVal() != null)
                                vmInternalCSName = ((CustomFieldStringValue)prop.getVal()).getValue();
                        }
                    }
                    String vmName = null;
                    if (vmInternalCSName != null && isUserVMInternalCSName(vmInternalCSName)) {
                        vmName = vmInternalCSName;
                    } else {
                        vmName = vmVcenterName;
                    }

                    if (s_logger.isTraceEnabled())
                        s_logger.trace("put " + vmName + " into host cache");

                    _vmCache.put(vmName, new VirtualMachineMO(_context, oc.getObj()));
                }
            }
        }
    }

};