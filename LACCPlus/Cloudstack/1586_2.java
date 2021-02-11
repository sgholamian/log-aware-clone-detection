//,temp,LibvirtComputingResource.java,1482,1496,temp,LibvirtComputingResource.java,1378,1388
//,3
public class xxx {
    private boolean checkOvsNetwork(final String networkName) {
        s_logger.debug("Checking if network " + networkName + " exists as openvswitch bridge");
        if (networkName == null) {
            return true;
        }

        final Script command = new Script("/bin/sh", _timeout);
        command.add("-c");
        command.add("ovs-vsctl br-exists " + networkName);
        return "0".equals(command.execute(null));
    }

};