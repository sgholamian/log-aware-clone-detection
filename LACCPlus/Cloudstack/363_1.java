//,temp,VpcManagerImpl.java,935,973,temp,VpcManagerImpl.java,693,733
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VPC_UPDATE, eventDescription = "updating vpc")
    public Vpc updateVpc(final long vpcId, final String vpcName, final String displayText, final String customId, final Boolean displayVpc) {
        CallContext.current().setEventDetails(" Id: " + vpcId);
        final Account caller = CallContext.current().getCallingAccount();

        // Verify input parameters
        final VpcVO vpcToUpdate = _vpcDao.findById(vpcId);
        if (vpcToUpdate == null) {
            throw new InvalidParameterValueException("Unable to find vpc by id " + vpcId);
        }

        _accountMgr.checkAccess(caller, null, false, vpcToUpdate);

        final VpcVO vpc = _vpcDao.createForUpdate(vpcId);

        if (vpcName != null) {
            vpc.setName(vpcName);
        }

        if (displayText != null) {
            vpc.setDisplayText(displayText);
        }

        if (customId != null) {
            vpc.setUuid(customId);
        }

        if (displayVpc != null) {
            vpc.setDisplay(displayVpc);
        }

        if (_vpcDao.update(vpcId, vpc)) {
            s_logger.debug("Updated VPC id=" + vpcId);
            return _vpcDao.findById(vpcId);
        } else {
            return null;
        }
    }

};