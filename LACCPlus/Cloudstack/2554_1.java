//,temp,ProjectManagerImpl.java,1003,1027,temp,ProjectManagerImpl.java,863,887
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_PROJECT_INVITATION_REMOVE, eventDescription = "removing project invitation", async = true)
    public boolean deleteProjectInvitation(long id) {
        Account caller = CallContext.current().getCallingAccount();

        ProjectInvitation invitation = _projectInvitationDao.findById(id);
        if (invitation == null) {
            throw new InvalidParameterValueException("Unable to find project invitation by id " + id);
        }

        //check that the project exists
        Project project = getProject(invitation.getProjectId());

        //check permissions - only project owner can remove the invitations
        _accountMgr.checkAccess(caller, AccessType.ModifyProject, true, _accountMgr.getAccount(project.getProjectAccountId()));

        if (_projectInvitationDao.remove(id)) {
            s_logger.debug("Project Invitation id=" + id + " is removed");
            return true;
        } else {
            s_logger.debug("Failed to remove project invitation id=" + id);
            return false;
        }
    }

};