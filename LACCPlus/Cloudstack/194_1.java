//,temp,AutoScaleManagerImpl.java,1228,1247,temp,AutoScaleManagerImpl.java,439,452
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_CONDITION_DELETE, eventDescription = "condition")
    public boolean deleteCondition(long conditionId) throws ResourceInUseException {
        /* Check if entity is in database */
        ConditionVO condition = getEntityInDatabase(CallContext.current().getCallingAccount(), "Condition", conditionId, _conditionDao);
        if (condition == null) {
            throw new InvalidParameterValueException("Unable to find Condition");
        }

        // Verify if condition is used in any autoscale policy
        if (_autoScalePolicyConditionMapDao.isConditionInUse(conditionId)) {
            s_logger.info("Cannot delete condition " + conditionId + " as it is being used in a condition.");
            throw new ResourceInUseException("Cannot delete Condition when it is in use by one or more AutoScale Policies.");
        }
        boolean success = _conditionDao.remove(conditionId);
        if (success) {
            s_logger.info("Successfully deleted condition " + condition.getId());
        }
        return success;
    }

};