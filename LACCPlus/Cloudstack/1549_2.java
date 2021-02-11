//,temp,ProjectInvitationDaoImpl.java,160,167,temp,ProjectAccountDaoImpl.java,144,153
//,3
public class xxx {
    @Override
    public void removeAccountFromProjects(long accountId) {
        SearchCriteria<ProjectAccountVO> sc = AllFieldsSearch.create();
        sc.setParameters("accountId", accountId);

        int rowsRemoved = remove(sc);
        if (rowsRemoved > 0) {
            s_logger.debug("Removed account id=" + accountId + " from " + rowsRemoved + " projects");
        }
    }

};