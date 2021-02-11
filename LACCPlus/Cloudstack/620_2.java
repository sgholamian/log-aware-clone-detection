//,temp,BridgeVifDriver.java,90,142,temp,LibvirtComputingResource.java,1203,1252
//,3
public class xxx {
    private void getPifs() {
        final File dir = new File("/sys/devices/virtual/net");
        final File[] netdevs = dir.listFiles();
        final List<String> bridges = new ArrayList<String>();
        for (int i = 0; i < netdevs.length; i++) {
            final File isbridge = new File(netdevs[i].getAbsolutePath() + "/bridge");
            final String netdevName = netdevs[i].getName();
            s_logger.debug("looking in file " + netdevs[i].getAbsolutePath() + "/bridge");
            if (isbridge.exists()) {
                s_logger.debug("Found bridge " + netdevName);
                bridges.add(netdevName);
            }
        }

        for (final String bridge : bridges) {
            s_logger.debug("looking for pif for bridge " + bridge);
            final String pif = getPif(bridge);
            if (isPublicBridge(bridge)) {
                _pifs.put("public", pif);
            }
            if (isGuestBridge(bridge)) {
                _pifs.put("private", pif);
            }
            _pifs.put(bridge, pif);
        }

        // guest(private) creates bridges on a pif, if private bridge not found try pif direct
        // This addresses the unnecessary requirement of someone to create an unused bridge just for traffic label
        if (_pifs.get("private") == null) {
            s_logger.debug("guest(private) traffic label '" + _guestBridgeName + "' not found as bridge, looking for physical interface");
            final File dev = new File("/sys/class/net/" + _guestBridgeName);
            if (dev.exists()) {
                s_logger.debug("guest(private) traffic label '" + _guestBridgeName + "' found as a physical device");
                _pifs.put("private", _guestBridgeName);
            }
        }

        // public creates bridges on a pif, if private bridge not found try pif direct
        // This addresses the unnecessary requirement of someone to create an unused bridge just for traffic label
        if (_pifs.get("public") == null) {
            s_logger.debug("public traffic label '" + _publicBridgeName+ "' not found as bridge, looking for physical interface");
            final File dev = new File("/sys/class/net/" + _publicBridgeName);
            if (dev.exists()) {
                s_logger.debug("public traffic label '" + _publicBridgeName + "' found as a physical device");
                _pifs.put("public", _publicBridgeName);
            }
        }

        s_logger.debug("done looking for pifs, no more bridges");
    }

};