//,temp,AutoScaleManagerImpl.java,1228,1247,temp,AutoScaleManagerImpl.java,439,452
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_AUTOSCALEVMPROFILE_DELETE, eventDescription = "deleting autoscale vm profile")
    public boolean deleteAutoScaleVmProfile(long id) {
        /* Check if entity is in database */
        getEntityInDatabase(CallContext.current().getCallingAccount(), "AutoScale Vm Profile", id, _autoScaleVmProfileDao);
        if (_autoScaleVmGroupDao.isProfileInUse(id)) {
            throw new InvalidParameterValueException("Cannot delete AutoScale Vm Profile when it is in use by one more vm groups");
        }
        boolean success = _autoScaleVmProfileDao.remove(id);
        if (success) {
            s_logger.info("Successfully deleted AutoScale Vm Profile with Id: " + id);
        }
        return success;
    }

};