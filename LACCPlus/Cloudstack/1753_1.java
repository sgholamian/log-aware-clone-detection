//,temp,NfsSecondaryStorageResource.java,2133,2151,temp,NfsSecondaryStorageResource.java,2120,2131
//,3
public class xxx {
    public String allowOutgoingOnPrivate(String destCidr) {
        if (!_inSystemVM) {
            return null;
        }
        Script command = new Script("/bin/bash", s_logger);
        String intf = "eth1";
        command.add("-c");
        command.add("iptables -I OUTPUT -o " + intf + " -d " + destCidr + " -p tcp -m state --state NEW -m tcp  -j ACCEPT");

        String result = command.execute();
        if (result != null) {
            s_logger.warn("Error in allowing outgoing to " + destCidr + ", err=" + result);
            return "Error in allowing outgoing to " + destCidr + ", err=" + result;
        }

        addRouteToInternalIpOrCidr(_localgw, _eth1ip, _eth1mask, destCidr);

        return null;
    }

};