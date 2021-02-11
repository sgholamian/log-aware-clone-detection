//,temp,ApiResponseHelper.java,2429,2446,temp,ServiceManagerImpl.java,233,250
//,3
public class xxx {
    private void populateAccount(ControlledEntityResponse response, long accountId) {
        Account account = ApiDBUtils.findAccountById(accountId);
        if (account == null) {
            s_logger.debug("Unable to find account with id: " + accountId);
        } else if (account.getType() == Account.ACCOUNT_TYPE_PROJECT) {
            // find the project
            Project project = ApiDBUtils.findProjectByProjectAccountId(account.getId());
            if (project != null) {
                response.setProjectId(project.getUuid());
                response.setProjectName(project.getName());
                response.setAccountName(account.getAccountName());
            } else {
                s_logger.debug("Unable to find project with id: " + account.getId());
            }
        } else {
            response.setAccountName(account.getAccountName());
        }
    }

};