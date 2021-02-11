//,temp,NetscalerResource.java,1523,1544,temp,NetscalerResource.java,1230,1256
//,3
public class xxx {
        private static void deleteVserverDomainBinding(final nitro_service client, final String vserverName, final String domainName) throws ExecutionException {
            try {
                final gslbvserver_domain_binding[] bindings = gslbvserver_domain_binding.get(client, vserverName);
                if (bindings != null) {
                    for (final gslbvserver_domain_binding binding : bindings) {
                        if (binding.get_domainname().equalsIgnoreCase(domainName)) {
                            gslbvserver_domain_binding.delete(client, binding);
                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Successfully deleted virtual server: " + vserverName + " and " + " domain: " + domainName + " binding");
                            }
                            break;
                        }
                    }
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to delete virtual server: " + vserverName + " and domain " + domainName + " binding due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};