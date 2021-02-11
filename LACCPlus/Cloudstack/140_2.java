//,temp,KVMHAProvider.java,87,101,temp,KVMHAProvider.java,71,85
//,3
public class xxx {
    @Override
    public boolean recover(Host r) throws HARecoveryException {
        try {
            if (outOfBandManagementService.isOutOfBandManagementEnabled(r)){
                final OutOfBandManagementResponse resp = outOfBandManagementService.executePowerOperation(r, PowerOperation.RESET, null);
                return resp.getSuccess();
            } else {
                LOG.warn("OOBM recover operation failed for the host " + r.getName());
                return false;
            }
        } catch (Exception e){
            LOG.warn("OOBM service is not configured or enabled for this host " + r.getName() + " error is " + e.getMessage());
            throw new HARecoveryException(" OOBM service is not configured or enabled for this host " + r.getName(), e);
        }
    }

};