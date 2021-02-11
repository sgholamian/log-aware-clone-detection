//,temp,NetscalerResource.java,1497,1521,temp,NetscalerResource.java,1443,1471
//,3
public class xxx {
        private static void createVserverDomainBinding(final nitro_service client, final String vserverName, final String domainName) throws ExecutionException {
            String errMsg;
            try {
                final gslbvserver_domain_binding binding = new gslbvserver_domain_binding();
                binding.set_domainname(domainName);
                binding.set_name(vserverName);
                gslbvserver_domain_binding.add(client, binding);
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully added virtual server: " + vserverName + " domain name: " + domainName + " binding");
                }
                return;
            } catch (final nitro_exception e) {
                if (e.getErrorCode() == NitroError.NS_GSLB_DOMAIN_ALREADY_BOUND) {
                    return;
                }
                errMsg = e.getMessage();
            } catch (final Exception e) {
                errMsg = e.getMessage();
            }
            errMsg = "Failed to create virtual server: " + vserverName + " domain name: " + domainName + " binding" + errMsg;
            if (s_logger.isDebugEnabled()) {
                s_logger.debug(errMsg);
            }
            throw new ExecutionException(errMsg);
        }

};