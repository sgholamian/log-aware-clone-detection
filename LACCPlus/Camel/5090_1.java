//,temp,BoxUsersManager.java,109,128,temp,BoxFilesManager.java,449,471
//,3
public class xxx {
    public BoxUser createEnterpriseUser(String login, String name, CreateUserParams params) {
        try {
            LOG.debug("Creating enterprise user with login={} name={}", login, name);
            if (login == null) {
                throw new IllegalArgumentException("Parameter 'login' can not be null");
            }
            if (name == null) {
                throw new IllegalArgumentException("Parameter 'name' can not be null");
            }

            if (params != null) {
                return BoxUser.createEnterpriseUser(boxConnection, login, name, params).getResource();
            } else {
                return BoxUser.createEnterpriseUser(boxConnection, login, name).getResource();
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};