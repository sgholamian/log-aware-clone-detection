//,temp,OvsElement.java,249,257,temp,BigSwitchBcfElement.java,356,363
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