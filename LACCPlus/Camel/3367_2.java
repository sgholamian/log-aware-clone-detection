//,temp,CustomIdFactoryTest.java,118,127,temp,DnsActivationPolicy.java,49,53
//,3
public class xxx {
    @Override
    public void onInit(Route route) {
        LOG.debug("onInit {}", route.getId());
        routes.put(route.getId(), route);
    }

};