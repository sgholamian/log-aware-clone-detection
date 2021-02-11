//,temp,HypervisorHostHelper.java,354,466,temp,HypervisorHostHelper.java,244,352
//,3
public class xxx {
    public static void updatePortProfile(VmwareContext context, String ethPortProfileName, String vethPortProfileName, Integer vlanId, Integer networkRateMbps,
            long peakBandwidth, long burstRate) throws Exception {
        NetconfHelper netconfClient = null;
        Map<String, String> vsmCredentials = getValidatedVsmCredentials(context);
        String vsmIp = vsmCredentials.get("vsmip");
        String vsmUserName = vsmCredentials.get("vsmusername");
        String vsmPassword = vsmCredentials.get("vsmpassword");

        String msg;
        try {
            netconfClient = new NetconfHelper(vsmIp, vsmUserName, vsmPassword);
        } catch (CloudRuntimeException e) {
            msg = "Failed to connect to Nexus 1000v " + vsmIp + " with credentials of user " + vsmUserName + ". Exception: " + e.toString();
            s_logger.error(msg);
            throw new CloudRuntimeException(msg);
        }

        PortProfile portProfile = netconfClient.getPortProfileByName(vethPortProfileName);
        int averageBandwidth = 0;
        String policyName = s_policyNamePrefix;
        if (networkRateMbps != null) {
            averageBandwidth = networkRateMbps.intValue();
            policyName += averageBandwidth;
        }

        if (averageBandwidth > 0) {
            PolicyMap policyMap = netconfClient.getPolicyMapByName(portProfile.inputPolicyMap);
            if (policyMap.committedRate == averageBandwidth && policyMap.peakRate == peakBandwidth && policyMap.burstRate == burstRate) {
                s_logger.debug("Detected that policy map is already applied to port profile " + vethPortProfileName);
                if (netconfClient != null) {
                    netconfClient.disconnect();
                    s_logger.debug("Disconnected Nexus 1000v session.");
                }
                return;
            } else {
                try {
                    // TODO(sateesh): Change the type of peakBandwidth &
                    // burstRate in PolicyMap to long.
                    s_logger.info("Adding policy map " + policyName);
                    netconfClient.addPolicyMap(policyName, averageBandwidth, (int)peakBandwidth, (int)burstRate);
                } catch (CloudRuntimeException e) {
                    msg =
                            "Failed to add policy map of " + policyName + " with parameters " + "committed rate = " + averageBandwidth + "peak bandwidth = " + peakBandwidth +
                            "burst size = " + burstRate + ". Exception: " + e.toString();
                    s_logger.error(msg);
                    if (netconfClient != null) {
                        netconfClient.disconnect();
                        s_logger.debug("Disconnected Nexus 1000v session.");
                    }
                    throw new CloudRuntimeException(msg);
                }

                try {
                    s_logger.info("Associating policy map " + policyName + " with port profile " + vethPortProfileName + ".");
                    netconfClient.attachServicePolicy(policyName, vethPortProfileName);
                } catch (CloudRuntimeException e) {
                    msg = "Failed to associate policy map " + policyName + " with port profile " + vethPortProfileName + ". Exception: " + e.toString();
                    s_logger.error(msg);
                    if (netconfClient != null) {
                        netconfClient.disconnect();
                        s_logger.debug("Disconnected Nexus 1000v session.");
                    }
                    throw new CloudRuntimeException(msg);
                }
            }
        }

        if (vlanId == null) {
            s_logger.info("Skipping update operation over ethernet port profile " + ethPortProfileName + " for untagged VLAN.");
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            return;
        }

        String currentVlan = portProfile.vlan;
        String newVlan = Integer.toString(vlanId.intValue());
        if (currentVlan.equalsIgnoreCase(newVlan)) {
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            return;
        }

        List<Pair<OperationType, String>> params = new ArrayList<Pair<OperationType, String>>();
        params.add(new Pair<OperationType, String>(OperationType.addvlanid, newVlan));
        try {
            s_logger.info("Updating vEthernet port profile with VLAN " + vlanId.toString());
            netconfClient.updatePortProfile(ethPortProfileName, SwitchPortMode.trunk, params);
        } catch (CloudRuntimeException e) {
            msg = "Failed to update ethernet port profile " + ethPortProfileName + " with parameters " + params.toString() + ". Exception: " + e.toString();
            s_logger.error(msg);
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            throw new CloudRuntimeException(msg);
        }

        try {
            netconfClient.updatePortProfile(vethPortProfileName, SwitchPortMode.access, params);
        } catch (CloudRuntimeException e) {
            msg = "Failed to update vEthernet port profile " + vethPortProfileName + " with parameters " + params.toString() + ". Exception: " + e.toString();
            s_logger.error(msg);
            if (netconfClient != null) {
                netconfClient.disconnect();
                s_logger.debug("Disconnected Nexus 1000v session.");
            }
            throw new CloudRuntimeException(msg);
        }
    }

};