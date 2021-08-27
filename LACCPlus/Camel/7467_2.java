//,temp,GitProducer.java,344,377,temp,GitProducer.java,310,342
//,3
public class xxx {
    protected void doCommit(Exchange exchange, String operation) throws Exception {
        String commitMessage = null;
        String username = null;
        String email = null;
        if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(GitConstants.GIT_COMMIT_MESSAGE))) {
            commitMessage = exchange.getIn().getHeader(GitConstants.GIT_COMMIT_MESSAGE, String.class);
        } else {
            throw new IllegalArgumentException("Commit message must be specified to execute " + operation);
        }
        if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(GitConstants.GIT_COMMIT_USERNAME))
                && ObjectHelper.isNotEmpty(exchange.getIn().getHeader(GitConstants.GIT_COMMIT_EMAIL))) {
            username = exchange.getIn().getHeader(GitConstants.GIT_COMMIT_USERNAME, String.class);
            email = exchange.getIn().getHeader(GitConstants.GIT_COMMIT_EMAIL, String.class);
        }
        boolean allowEmpty = endpoint.isAllowEmpty();
        if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(GitConstants.GIT_ALLOW_EMPTY))) {
            allowEmpty = exchange.getIn().getHeader(GitConstants.GIT_ALLOW_EMPTY, Boolean.class);
        }

        try {
            if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
                git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
            }
            if (ObjectHelper.isNotEmpty(username) && ObjectHelper.isNotEmpty(email)) {
                git.commit().setAllowEmpty(allowEmpty).setCommitter(username, email).setMessage(commitMessage).call();
            } else {
                git.commit().setAllowEmpty(allowEmpty).setMessage(commitMessage).call();
            }
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
    }

};