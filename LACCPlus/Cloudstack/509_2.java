//,temp,VmwareResource.java,5803,5853,temp,VmwareResource.java,5751,5801
//,3
public class xxx {
    private HashMap<String, HostVmStateReportEntry> getHostVmStateReport() throws Exception {
        VmwareHypervisorHost hyperHost = getHyperHost(getServiceContext());

        int key = ((HostMO)hyperHost).getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }
        String instanceNameCustomField = "value[" + key + "]";

        // CLOUD_VM_INTERNAL_NAME stores the internal CS generated vm name. This was earlier stored in name. Now, name can be either the hostname or
        // the internal CS name, but the custom field CLOUD_VM_INTERNAL_NAME always stores the internal CS name.
        ObjectContent[] ocs = hyperHost.getVmPropertiesOnHyperHost(new String[] {"name", "runtime.powerState", "config.template", instanceNameCustomField});

        HashMap<String, HostVmStateReportEntry> newStates = new HashMap<String, HostVmStateReportEntry>();
        if (ocs != null && ocs.length > 0) {
            for (ObjectContent oc : ocs) {
                List<DynamicProperty> objProps = oc.getPropSet();
                if (objProps != null) {

                    boolean isTemplate = false;
                    String name = null;
                    String VMInternalCSName = null;
                    VirtualMachinePowerState powerState = VirtualMachinePowerState.POWERED_OFF;
                    for (DynamicProperty objProp : objProps) {
                        if (objProp.getName().equals("config.template")) {
                            if (objProp.getVal().toString().equalsIgnoreCase("true")) {
                                isTemplate = true;
                            }
                        } else if (objProp.getName().equals("runtime.powerState")) {
                            powerState = (VirtualMachinePowerState)objProp.getVal();
                        } else if (objProp.getName().equals("name")) {
                            name = (String)objProp.getVal();
                        } else if (objProp.getName().contains(instanceNameCustomField)) {
                            if (objProp.getVal() != null)
                                VMInternalCSName = ((CustomFieldStringValue)objProp.getVal()).getValue();
                        } else {
                            assert (false);
                        }
                    }

                    if (VMInternalCSName != null)
                        name = VMInternalCSName;

                    if (!isTemplate) {
                        newStates.put(name, new HostVmStateReportEntry(convertPowerState(powerState), hyperHost.getHyperHostName()));
                    }
                }
            }
        }
        return newStates;
    }

};