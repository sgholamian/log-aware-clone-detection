//,temp,BigSwitchBcfElement.java,356,363,temp,CiscoVnmcElement.java,477,484
//,2
public class xxx {
    @Override
    public boolean verifyServicesCombination(Set<Service> services) {
        if (!services.contains(Service.Firewall)) {
            s_logger.warn("CiscoVnmc must be used as Firewall Service Provider in the network");
            return false;
        }
        return true;
    }

};