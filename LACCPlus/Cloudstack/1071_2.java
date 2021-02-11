//,temp,AssociateUcsProfileToBladeCmd.java,51,62,temp,AddBaremetalRctCmd.java,66,75
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
        try {
            BaremetalRctResponse rsp = vlanMgr.addRct(this);
            this.setResponseObject(rsp);
        } catch (Exception e) {
            s_logger.warn(String.format("unable to add baremetal RCT[%s]", getRctUrl()), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};