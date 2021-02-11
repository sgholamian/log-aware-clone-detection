//,temp,HypervisorHostHelper.java,354,466,temp,HypervisorHostHelper.java,244,352
//,3
public class xxx {
    public static void createPortProfile(VmwareContext context, String ethPortProfileName, String networkName, Integer vlanId, Integer networkRateMbps,
            long peakBandwidth, long burstSize, String gateway, boolean configureVServiceInNexus) throws Exception {
        Map<String, String> vsmCredentials = getValidatedVsmCredentials(context);
        String vsmIp = vsmCredentials.get("vsmip");
        String vsmUserName = vsmCredentials.get("vsmusername");
        String vsmPassword = vsmCredentials.get("vsmpassword");
        String msg;

        NetconfHelper netconfClient;
        try {
            s_logger.info("Connecting to Nexus 1000v: " + vsmIp);
            netconfClient = new NetconfHelper(vsmIp, vsmUserName, vsmPassword);
            s_logger.info("Successfully connected to Nexus 1000v : " + vsmIp);
        } catch (CloudRuntimeException e) {
            msg = "Failed to connect to Nexus 1000v " + vsmIp + " with credentials of user " + vsmUserName + ". Exception: " + e.toString();
            s_logger.error(msg);
            throw new CloudRuntimeException(msg);
        }

        String policyName = s_policyNamePrefix;
        int averageBandwidth = 0;
        if (networkRateMbps != null) {
            averageBandwidth = networkRateMbps.intValue();
            policyName += averageBandwidth;
        }

        try {
            // TODO(sateesh): Change the type of peakBandwidth & burstRate in
            // PolicyMap to long.
            if (averageBandwidth > 0) {
                s_logger.debug("Adding policy map " + policyName);
                netconfClient.addPolicyMap(policyName, averageBandwidth, (int)peakBandwidth, (int)burstSize);
            }
        } catch (CloudRuntimeException e) {
            msg =
                    "Failed to add policy map of " + policyName + " with parameters " + "committed rate = " + averageBandwidth + "peak bandwidth = " + peakBandwidth +
                    "burst size = " + burstSize + ". Exception: " + e.toString();
            s_logger.error(msg);
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            throw new CloudRuntimeException(msg);
        }

        List<Pair<OperationType, String>> params = new ArrayList<Pair<OperationType, String>>();
        if (vlanId != null) {
            // No need to update ethernet port profile for untagged vlans
            params.add(new Pair<OperationType, String>(OperationType.addvlanid, vlanId.toString()));
            try {
                s_logger.info("Updating Ethernet port profile " + ethPortProfileName + " with VLAN " + vlanId);
                netconfClient.updatePortProfile(ethPortProfileName, SwitchPortMode.trunk, params);
                s_logger.info("Added " + vlanId + " to Ethernet port profile " + ethPortProfileName);
            } catch (CloudRuntimeException e) {
                msg = "Failed to update Ethernet port profile " + ethPortProfileName + " with VLAN " + vlanId + ". Exception: " + e.toString();
                s_logger.error(msg);
                if (netconfClient != null) {
                    netconfClient.disconnect();
                    s_logger.debug("Disconnected Nexus 1000v session.");
                }
                throw new CloudRuntimeException(msg);
            }
        }

        try {
            if (vlanId == null) {
                s_logger.info("Adding port profile configured over untagged VLAN.");
                netconfClient.addPortProfile(networkName, PortProfileType.vethernet, BindingType.portbindingstatic, SwitchPortMode.access, 0);
            } else {
                if (!configureVServiceInNexus) {
                    s_logger.info("Adding port profile configured over VLAN : " + vlanId.toString());
                    netconfClient.addPortProfile(networkName, PortProfileType.vethernet, BindingType.portbindingstatic, SwitchPortMode.access, vlanId.intValue());
                } else {
                    String tenant = "vlan-" + vlanId.intValue();
                    String vdc = "root/" + tenant + "/VDC-" + tenant;
                    String esp = "ESP-" + tenant;
                    s_logger.info("Adding vservice node in Nexus VSM for VLAN : " + vlanId.toString());
                    netconfClient.addVServiceNode(vlanId.toString(), gateway);
                    s_logger.info("Adding port profile with vservice details configured over VLAN : " + vlanId.toString());
                    netconfClient.addPortProfile(networkName, PortProfileType.vethernet, BindingType.portbindingstatic, SwitchPortMode.access, vlanId.intValue(), vdc,
                            esp);
                }
            }
        } catch (CloudRuntimeException e) {
            msg = "Failed to add vEthernet port profile " + networkName + "." + ". Exception: " + e.toString();
            s_logger.error(msg);
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            throw new CloudRuntimeException(msg);
        }

        try {
            if (averageBandwidth > 0) {
                s_logger.info("Associating policy map " + policyName + " with port profile " + networkName + ".");
                netconfClient.attachServicePolicy(policyName, networkName);
            }
        } catch (CloudRuntimeException e) {
            msg = "Failed to associate policy map " + policyName + " with port profile " + networkName + ". Exception: " + e.toString();
            s_logger.error(msg);
            throw new CloudRuntimeException(msg);
        } finally {
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
        }
    }

};