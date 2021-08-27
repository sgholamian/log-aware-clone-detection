//,temp,DigitalOceanBlockStoragesProducer.java,166,188,temp,DigitalOceanKeysProducer.java,129,151
//,3
public class xxx {
    private void updateKey(Exchange exchange) throws Exception {
        Integer keyId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        String fingerprint = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_FINGERPRINT, String.class);
        Key key;

        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }

        if (ObjectHelper.isNotEmpty(keyId)) {
            key = getEndpoint().getDigitalOceanClient().updateKey(keyId, name);
        } else if (ObjectHelper.isNotEmpty(fingerprint)) {
            key = getEndpoint().getDigitalOceanClient().updateKey(fingerprint, name);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.KEY_FINGERPRINT + " must be specified");
        }

        LOG.trace("Update Key [{}] ", key);
        exchange.getMessage().setBody(key);
    }

};