//,temp,KVMHAProvider.java,87,101,temp,KVMHAProvider.java,71,85
//,3
public class xxx {
    @Override
    public boolean fence(Host r) throws HAFenceException {
        try {
            if (outOfBandManagementService.isOutOfBandManagementEnabled(r)){
                final OutOfBandManagementResponse resp = outOfBandManagementService.executePowerOperation(r, PowerOperation.OFF, null);
                return resp.getSuccess();
            } else {
                LOG.warn("OOBM fence operation failed for this host " + r.getName());
                return false;
            }
        } catch (Exception e){
            LOG.warn("OOBM service is not configured or enabled for this host " + r.getName() + " error is " + e.getMessage());
            throw new HAFenceException("OOBM service is not configured or enabled for this host " + r.getName() , e);
        }
    }

};