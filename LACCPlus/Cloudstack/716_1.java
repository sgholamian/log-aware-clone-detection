//,temp,JuniperSRXExternalFirewallElement.java,524,531,temp,PaloAltoExternalFirewallElement.java,430,437
//,2
public class xxx {
    @Override
    public boolean verifyServicesCombination(Set<Service> services) {
        if (!services.contains(Service.Firewall)) {
            s_logger.warn("SRX must be used as Firewall Service Provider in the network");
            return false;
        }
        return true;
    }

};