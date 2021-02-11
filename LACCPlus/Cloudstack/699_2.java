//,temp,AutoScaleManagerImpl.java,1073,1098,temp,AutoScaleManagerImpl.java,1046,1071
//,2
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_AUTOSCALEVMGROUP_ENABLE, eventDescription = "enabling autoscale vm group", async = true)
    public AutoScaleVmGroup enableAutoScaleVmGroup(Long id) {
        AutoScaleVmGroupVO vmGroup = getEntityInDatabase(CallContext.current().getCallingAccount(), "AutoScale Vm Group", id, _autoScaleVmGroupDao);
        boolean success = false;
        if (!vmGroup.getState().equals(AutoScaleVmGroup.State_Disabled)) {
            throw new InvalidParameterValueException("Only a AutoScale Vm Group which is in Disabled state can be enabled.");
        }

        try {
            vmGroup.setState(AutoScaleVmGroup.State_Enabled);
            vmGroup = _autoScaleVmGroupDao.persist(vmGroup);
            success = configureAutoScaleVmGroup(id, AutoScaleVmGroup.State_Disabled);
        } catch (ResourceUnavailableException e) {
            vmGroup.setState(AutoScaleVmGroup.State_Disabled);
            _autoScaleVmGroupDao.persist(vmGroup);
        } finally {
            if (!success) {
                s_logger.warn("Failed to enable AutoScale Vm Group id : " + id);
                return null;
            }
            s_logger.info("Successfully enabled AutoScale Vm Group with Id:" + id);
        }
        return vmGroup;
    }

};