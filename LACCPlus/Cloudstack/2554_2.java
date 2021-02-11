//,temp,ProjectManagerImpl.java,1003,1027,temp,ProjectManagerImpl.java,863,887
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_PROJECT_SUSPEND, eventDescription = "suspending project", async = true)
    public Project suspendProject(long projectId) throws ConcurrentOperationException, ResourceUnavailableException {
        Account caller = CallContext.current().getCallingAccount();

        ProjectVO project = getProject(projectId);
        //verify input parameters
        if (project == null) {
            InvalidParameterValueException ex = new InvalidParameterValueException("Unable to find project with specified id");
            ex.addProxyObject(String.valueOf(projectId), "projectId");
            throw ex;
        }

        _accountMgr.checkAccess(caller, AccessType.ModifyProject, true, _accountMgr.getAccount(project.getProjectAccountId()));

        if (suspendProject(project)) {
            s_logger.debug("Successfully suspended project id=" + projectId);
            return _projectDao.findById(projectId);
        } else {
            CloudRuntimeException ex = new CloudRuntimeException("Failed to suspend project with specified id");
            ex.addProxyObject(project.getUuid(), "projectId");
            throw ex;
        }

    }

};