//,temp,AutoScaleManagerImpl.java,1073,1098,temp,AutoScaleManagerImpl.java,823,871
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_AUTOSCALEVMGROUP_DISABLE, eventDescription = "disabling autoscale vm group", async = true)
    @DB
    public AutoScaleVmGroup disableAutoScaleVmGroup(Long id) {
        AutoScaleVmGroupVO vmGroup = getEntityInDatabase(CallContext.current().getCallingAccount(), "AutoScale Vm Group", id, _autoScaleVmGroupDao);
        boolean success = false;
        if (!vmGroup.getState().equals(AutoScaleVmGroup.State_Enabled)) {
            throw new InvalidParameterValueException("Only a AutoScale Vm Group which is in Enabled state can be disabled.");
        }

        try {
            vmGroup.setState(AutoScaleVmGroup.State_Disabled);
            vmGroup = _autoScaleVmGroupDao.persist(vmGroup);
            success = configureAutoScaleVmGroup(id, AutoScaleVmGroup.State_Enabled);
        } catch (ResourceUnavailableException e) {
            vmGroup.setState(AutoScaleVmGroup.State_Enabled);
            _autoScaleVmGroupDao.persist(vmGroup);
        } finally {
            if (!success) {
                s_logger.warn("Failed to disable AutoScale Vm Group id : " + id);
                return null;
            }
            s_logger.info("Successfully disabled AutoScale Vm Group with Id:" + id);
        }
        return vmGroup;
    }

};