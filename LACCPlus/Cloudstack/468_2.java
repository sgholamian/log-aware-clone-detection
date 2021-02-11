//,temp,JuniperSrxResource.java,3059,3095,temp,JuniperSrxResource.java,3023,3057
//,3
public class xxx {
    private boolean removeSecurityPolicyAndApplications(SecurityPolicyType type, String privateIp) throws ExecutionException {
        if (!manageSecurityPolicy(type, SrxCommand.CHECK_IF_EXISTS, null, null, privateIp, null, null, null, false)) {
            return true;
        }

        if (manageSecurityPolicy(type, SrxCommand.CHECK_IF_IN_USE, null, null, privateIp, null, null, null, false)) {
            return true;
        }

        // Get a list of applications for this security policy
        List<String> applications = getApplicationsForSecurityPolicy(type, privateIp, _publicZone, _privateZone);

        // Remove the security policy
        manageSecurityPolicy(type, SrxCommand.DELETE, null, null, privateIp, null, null, null, false);

        // Remove any applications for the removed security policy that are no longer in use
        List<String> unusedApplications = getUnusedApplications(applications, _publicZone, _privateZone);
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

        return true;
    }

};