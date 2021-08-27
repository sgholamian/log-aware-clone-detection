//,temp,BoxGroupsManager.java,82,99,temp,BoxFilesManager.java,556,579
//,3
public class xxx {
    public BoxGroup createGroup(
            String name, String provenance,
            String externalSyncIdentifier, String description,
            String invitabilityLevel, String memberViewabilityLevel) {

        try {
            LOG.debug("Creating group name={}", name);
            if (name == null) {
                throw new IllegalArgumentException("Parameter 'name' can not be null");
            }

            return BoxGroup.createGroup(boxConnection, name, provenance, externalSyncIdentifier, description,
                    invitabilityLevel, memberViewabilityLevel).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};