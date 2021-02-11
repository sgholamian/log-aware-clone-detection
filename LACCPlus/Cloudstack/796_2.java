//,temp,DomainManagerImpl.java,344,349,temp,ProjectManagerImpl.java,722,726
//,3
public class xxx {
    private boolean expireInvitation(ProjectInvitationVO invite) {
        s_logger.debug("Expiring invitation id=" + invite.getId());
        invite.setState(ProjectInvitation.State.Expired);
        return _projectInvitationDao.update(invite.getId(), invite);
    }

};