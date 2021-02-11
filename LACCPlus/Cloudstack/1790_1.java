//,temp,CreateApplicationLoadBalancerCmd.java,200,217,temp,CreateAutoScaleVmGroupCmd.java,211,232
//,3
public class xxx {
    @Override
    public void execute() throws ResourceAllocationException, ResourceUnavailableException {
        ApplicationLoadBalancerRule rule = null;
        try {
            CallContext.current().setEventDetails("Load Balancer Id: " + getEntityId());
            // State might be different after the rule is applied, so get new object here
            rule = _entityMgr.findById(ApplicationLoadBalancerRule.class, getEntityId());
            ApplicationLoadBalancerResponse lbResponse = _responseGenerator.createLoadBalancerContainerReponse(rule, _lbService.getLbInstances(getEntityId()));
            setResponseObject(lbResponse);
            lbResponse.setResponseName(getCommandName());
        } catch (Exception ex) {
            s_logger.warn("Failed to create load balancer due to exception ", ex);
        } finally {
            if (rule == null) {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create load balancer");
            }
        }
    }

};