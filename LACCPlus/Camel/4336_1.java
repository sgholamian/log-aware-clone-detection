//,temp,UndertowComponent.java,175,196,temp,UndertowEndpoint.java,530,554
//,3
public class xxx {
    private void initSecurityProvider() throws Exception {
        Object securityConfiguration = getSecurityConfiguration();
        if (securityConfiguration != null) {
            ServiceLoader<UndertowSecurityProvider> securityProvider = ServiceLoader.load(UndertowSecurityProvider.class);

            Iterator<UndertowSecurityProvider> iter = securityProvider.iterator();
            List<String> providers = new LinkedList();
            while (iter.hasNext()) {
                UndertowSecurityProvider security = iter.next();
                //only securityProvider, who accepts security configuration, could be used
                if (security.acceptConfiguration(securityConfiguration, null)) {
                    this.securityProvider = security;
                    LOG.info("Security provider found {}", securityProvider.getClass().getName());
                    break;
                }
                providers.add(security.getClass().getName());
            }
            if (this.securityProvider == null) {
                LOG.info("Security provider for configuration {} not found {}", securityConfiguration, providers);
            }
        }
    }

};