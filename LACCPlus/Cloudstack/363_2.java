//,temp,VpcManagerImpl.java,935,973,temp,VpcManagerImpl.java,693,733
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VPC_OFFERING_UPDATE, eventDescription = "updating vpc offering")
    public VpcOffering updateVpcOffering(final long vpcOffId, final String vpcOfferingName, final String displayText, final String state) {
        CallContext.current().setEventDetails(" Id: " + vpcOffId);

        // Verify input parameters
        final VpcOfferingVO offeringToUpdate = _vpcOffDao.findById(vpcOffId);
        if (offeringToUpdate == null) {
            throw new InvalidParameterValueException("Unable to find vpc offering " + vpcOffId);
        }

        final VpcOfferingVO offering = _vpcOffDao.createForUpdate(vpcOffId);

        if (vpcOfferingName != null) {
            offering.setName(vpcOfferingName);
        }

        if (displayText != null) {
            offering.setDisplayText(displayText);
        }

        if (state != null) {
            boolean validState = false;
            for (final VpcOffering.State st : VpcOffering.State.values()) {
                if (st.name().equalsIgnoreCase(state)) {
                    validState = true;
                    offering.setState(st);
                }
            }
            if (!validState) {
                throw new InvalidParameterValueException("Incorrect state value: " + state);
            }
        }

        if (_vpcOffDao.update(vpcOffId, offering)) {
            s_logger.debug("Updated VPC offeirng id=" + vpcOffId);
            return _vpcOffDao.findById(vpcOffId);
        } else {
            return null;
        }
    }

};