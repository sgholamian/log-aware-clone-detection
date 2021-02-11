//,temp,CloudianClient.java,212,230,temp,CloudianClient.java,192,210
//,3
public class xxx {
    public List<CloudianUser> listUsers(final String groupId) {
        if (Strings.isNullOrEmpty(groupId)) {
            return new ArrayList<>();
        }
        LOG.debug("Trying to list Cloudian users in group id=" + groupId);
        try {
            final HttpResponse response = get(String.format("/user/list?groupId=%s&userType=all&userStatus=active", groupId));
            checkResponseOK(response);
            if (checkEmptyResponse(response)) {
                return new ArrayList<>();
            }
            final ObjectMapper mapper = new ObjectMapper();
            return Arrays.asList(mapper.readValue(response.getEntity().getContent(), CloudianUser[].class));
        } catch (final IOException e) {
            LOG.error("Failed to list Cloudian users due to:", e);
            checkResponseTimeOut(e);
        }
        return new ArrayList<>();
    }

};