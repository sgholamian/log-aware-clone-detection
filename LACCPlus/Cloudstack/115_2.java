//,temp,CloudianClient.java,301,316,temp,CloudianClient.java,192,210
//,3
public class xxx {
    public CloudianUser listUser(final String userId, final String groupId) {
        if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(groupId)) {
            return null;
        }
        LOG.debug("Trying to find Cloudian user with id=" + userId + " and group id=" + groupId);
        try {
            final HttpResponse response = get(String.format("/user?userId=%s&groupId=%s", userId, groupId));
            checkResponseOK(response);
            if (checkEmptyResponse(response)) {
                return null;
            }
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getEntity().getContent(), CloudianUser.class);
        } catch (final IOException e) {
            LOG.error("Failed to list Cloudian user due to:", e);
            checkResponseTimeOut(e);
        }
        return null;
    }

};