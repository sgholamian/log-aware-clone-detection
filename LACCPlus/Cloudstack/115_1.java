//,temp,CloudianClient.java,301,316,temp,CloudianClient.java,192,210
//,3
public class xxx {
    public List<CloudianGroup> listGroups() {
        LOG.debug("Trying to list Cloudian groups");
        try {
            final HttpResponse response = get("/group/list");
            checkResponseOK(response);
            if (checkEmptyResponse(response)) {
                return new ArrayList<>();
            }
            final ObjectMapper mapper = new ObjectMapper();
            return Arrays.asList(mapper.readValue(response.getEntity().getContent(), CloudianGroup[].class));
        } catch (final IOException e) {
            LOG.error("Failed to list Cloudian groups due to:", e);
            checkResponseTimeOut(e);
        }
        return new ArrayList<>();
    }

};