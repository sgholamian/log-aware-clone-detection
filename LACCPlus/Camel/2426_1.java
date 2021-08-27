//,temp,BoxUsersManager.java,80,99,temp,BoxGroupsManager.java,270,288
//,3
public class xxx {
    public List<BoxUser.Info> getAllEnterpriseOrExternalUsers(String filterTerm, String... fields) {
        try {
            LOG.debug("Getting all enterprise users matching filterTerm={}", filterTerm);

            List<BoxUser.Info> users = new ArrayList<>();
            Iterable<BoxUser.Info> iterable;
            if (filterTerm == null) {
                iterable = BoxUser.getAllEnterpriseUsers(boxConnection);
            } else {
                iterable = BoxUser.getAllEnterpriseUsers(boxConnection, filterTerm, fields);
            }
            for (BoxUser.Info info : iterable) {
                users.add(info);
            }
            return users;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};