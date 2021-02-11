//,temp,ProjectManagerImpl.java,377,395,temp,SnapshotSchedulerImpl.java,408,421
//,3
public class xxx {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
        boolean success = true;

        //remove account
        ProjectAccountVO projectAccount = _projectAccountDao.findByProjectIdAccountId(projectId, accountId);
        success = _projectAccountDao.remove(projectAccount.getId());

        //remove all invitations for account
        if (success) {
            s_logger.debug("Removed account " + accountId + " from project " + projectId + " , cleaning up old invitations for account/project...");
            ProjectInvitation invite = _projectInvitationDao.findByAccountIdProjectId(accountId, projectId);
            if (invite != null) {
                success = success && _projectInvitationDao.remove(invite.getId());
            }
        }

        return success;
    }

};