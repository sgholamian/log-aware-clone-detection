//,temp,BridgeVifDriver.java,155,180,temp,LibvirtComputingResource.java,1294,1319
//,3
public class xxx {
    private String matchPifFileInDirectory(final String bridgeName) {
        final File brif = new File("/sys/devices/virtual/net/" + bridgeName + "/brif");

        if (!brif.isDirectory()) {
            final File pif = new File("/sys/class/net/" + bridgeName);
            if (pif.isDirectory()) {
                // if bridgeName already refers to a pif, return it as-is
                return bridgeName;
            }
            s_logger.debug("failing to get physical interface from bridge " + bridgeName + ", does " + brif.getAbsolutePath() + "exist?");
            return "";
        }

        final File[] interfaces = brif.listFiles();

        for (File anInterface : interfaces) {
            final String fname = anInterface.getName();
            s_logger.debug("matchPifFileInDirectory: file name '" + fname + "'");
            if (LibvirtComputingResource.isInterface(fname)) {
                return fname;
            }
        }

        s_logger.debug("failing to get physical interface from bridge " + bridgeName + ", did not find an eth*, bond*, team*, vlan*, em*, p*p*, ens*, eno*, enp*, or enx* in " + brif.getAbsolutePath());
        return "";
    }

};