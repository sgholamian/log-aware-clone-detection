//,temp,BridgeVifDriver.java,90,142,temp,LibvirtComputingResource.java,1203,1252
//,3
public class xxx {
    public void getPifs() {
        final File dir = new File("/sys/devices/virtual/net");
        final File[] netdevs = dir.listFiles();
        final List<String> bridges = new ArrayList<String>();
        for (File netdev : netdevs) {
            final File isbridge = new File(netdev.getAbsolutePath() + "/bridge");
            final String netdevName = netdev.getName();
            s_logger.debug("looking in file " + netdev.getAbsolutePath() + "/bridge");
            if (isbridge.exists()) {
                s_logger.debug("Found bridge " + netdevName);
                bridges.add(netdevName);
            }
        }

        String guestBridgeName = _libvirtComputingResource.getGuestBridgeName();
        String publicBridgeName = _libvirtComputingResource.getPublicBridgeName();

        for (final String bridge : bridges) {
            s_logger.debug("looking for pif for bridge " + bridge);
            final String pif = getPif(bridge);
            if (_libvirtComputingResource.isPublicBridge(bridge)) {
                _pifs.put("public", pif);
            }
            if (guestBridgeName != null && bridge.equals(guestBridgeName)) {
                _pifs.put("private", pif);
            }
            _pifs.put(bridge, pif);
        }

        // guest(private) creates bridges on a pif, if private bridge not found try pif direct
        // This addresses the unnecessary requirement of someone to create an unused bridge just for traffic label
        if (_pifs.get("private") == null) {
            s_logger.debug("guest(private) traffic label '" + guestBridgeName + "' not found as bridge, looking for physical interface");
            final File dev = new File("/sys/class/net/" + guestBridgeName);
            if (dev.exists()) {
                s_logger.debug("guest(private) traffic label '" + guestBridgeName + "' found as a physical device");
                _pifs.put("private", guestBridgeName);
            }
        }

        // public creates bridges on a pif, if private bridge not found try pif direct
        // This addresses the unnecessary requirement of someone to create an unused bridge just for traffic label
        if (_pifs.get("public") == null) {
            s_logger.debug("public traffic label '" + publicBridgeName+ "' not found as bridge, looking for physical interface");
            final File dev = new File("/sys/class/net/" + publicBridgeName);
            if (dev.exists()) {
                s_logger.debug("public traffic label '" + publicBridgeName + "' found as a physical device");
                _pifs.put("public", publicBridgeName);
            }
        }

        s_logger.debug("done looking for pifs, no more bridges");
    }

};