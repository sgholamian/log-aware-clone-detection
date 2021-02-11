//,temp,PaloAltoExternalFirewallElement.java,430,437,temp,BrocadeVcsElement.java,233,241
//,2
public class xxx {
    @Override
    public boolean verifyServicesCombination(Set<Service> services) {

        if (!services.contains(Service.Connectivity)) {
            s_logger.warn("Unable to provide services without Connectivity service enabled for this element");
            return false;
        }
        return true;
    }

};