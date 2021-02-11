//,temp,DatacenterMO.java,128,158,temp,HostMO.java,607,647
//,3
public class xxx {
    public HashMap<String, Integer> getVmVncPortsOnHost() throws Exception {

        int key = getCustomFieldKey("VirtualMachine", CustomFieldConstants.CLOUD_VM_INTERNAL_NAME);
        if (key == 0) {
            s_logger.warn("Custom field " + CustomFieldConstants.CLOUD_VM_INTERNAL_NAME + " is not registered ?!");
        }

        ObjectContent[] ocs = getVmPropertiesOnHyperHost(new String[] {"name", "config.extraConfig[\"RemoteDisplay.vnc.port\"]", "value[" + key + "]"});

        HashMap<String, Integer> portInfo = new HashMap<String, Integer>();
        if (ocs != null && ocs.length > 0) {
            for (ObjectContent oc : ocs) {
                List<DynamicProperty> objProps = oc.getPropSet();
                if (objProps != null) {
                    String vmName = null;
                    String value = null;
                    String vmInternalCSName = null;
                    for (DynamicProperty objProp : objProps) {
                        if (objProp.getName().equals("name")) {
                            vmName = (String)objProp.getVal();
                        } else if (objProp.getName().startsWith("value[")) {
                            if (objProp.getVal() != null)
                                vmInternalCSName = ((CustomFieldStringValue)objProp.getVal()).getValue();
                        } else {
                            OptionValue optValue = (OptionValue)objProp.getVal();
                            value = (String)optValue.getValue();
                        }
                    }

                    if (vmInternalCSName != null && isUserVMInternalCSName(vmInternalCSName))
                        vmName = vmInternalCSName;

                    if (vmName != null && value != null) {
                        portInfo.put(vmName, Integer.parseInt(value));
                    }
                }
            }
        }

        return portInfo;
    }

};