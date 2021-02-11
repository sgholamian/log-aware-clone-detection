//,temp,CloudianClient.java,281,299,temp,CloudianClient.java,212,230
//,3
public class xxx {
    public CloudianGroup listGroup(final String groupId) {
        if (Strings.isNullOrEmpty(groupId)) {
            return null;
        }
        LOG.debug("Trying to find Cloudian group with id=" + groupId);
        try {
            final HttpResponse response = get(String.format("/group?groupId=%s", groupId));
            checkResponseOK(response);
            if (checkEmptyResponse(response)) {
                return null;
            }
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getEntity().getContent(), CloudianGroup.class);
        } catch (final IOException e) {
            LOG.error("Failed to list Cloudian group due to:", e);
            checkResponseTimeOut(e);
        }
        return null;
    }

};