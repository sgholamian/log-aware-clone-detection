//,temp,JuniperSrxResource.java,3059,3095,temp,JuniperSrxResource.java,3023,3057
//,3
public class xxx {
    private boolean removeEgressSecurityPolicyAndApplications(SecurityPolicyType type, String guestVlan, List<String> cidrs, boolean defaultEgressAction)
        throws ExecutionException {
        if (!manageSecurityPolicy(type, SrxCommand.CHECK_IF_EXISTS, null, null, guestVlan, null, cidrs, null, defaultEgressAction)) {
            return true;
        }
        // Get a list of applications for this security policy
        List<String> applications;
        applications = getApplicationsForSecurityPolicy(type, guestVlan, _privateZone, _publicZone);

        // Remove the security policy even if it is in use
        manageSecurityPolicy(type, SrxCommand.DELETE, null, null, guestVlan, null, cidrs, null, defaultEgressAction);

        // Remove any applications for the removed security policy that are no longer in use
        List<String> unusedApplications;
        unusedApplications = getUnusedApplications(applications, _privateZone, _publicZone);

        for (String application : unusedApplications) {
            Object[] applicationComponents;

            try {
                applicationComponents = parseApplicationName(type, application);
            } catch (ExecutionException e) {
                s_logger.error("Found an invalid application: " + application + ". Not attempting to clean up.");
                continue;
            }

            Protocol protocol = (Protocol)applicationComponents[0];
            Integer startPort = (Integer)applicationComponents[1];
            Integer endPort = (Integer)applicationComponents[2];
            manageApplication(type, SrxCommand.DELETE, protocol, startPort, endPort);
        }
        for (String cidr : cidrs) {
            manageAddressBookEntry(SrxCommand.DELETE, _publicZone, cidr, null);
        }

        return true;
    }

};