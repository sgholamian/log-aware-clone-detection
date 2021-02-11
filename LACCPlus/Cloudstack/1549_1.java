//,temp,ProjectInvitationDaoImpl.java,160,167,temp,ProjectAccountDaoImpl.java,144,153
//,3
public class xxx {
    @Override
    public void cleanupInvitations(long projectId) {
        SearchCriteria<ProjectInvitationVO> sc = AllFieldsSearch.create();
        sc.setParameters("projectId", projectId);

        int numberRemoved = remove(sc);
        s_logger.debug("Removed " + numberRemoved + " invitations for project id=" + projectId);
    }

};