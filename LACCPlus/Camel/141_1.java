//,temp,DigitalOceanKeysProducer.java,85,101,temp,DigitalOceanKeysProducer.java,62,77
//,2
public class xxx {
    private void deleteKey(Exchange exchange) throws Exception {
        Integer keyId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        String fingerprint = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_FINGERPRINT, String.class);
        Delete delete;

        if (ObjectHelper.isNotEmpty(keyId)) {
            delete = getEndpoint().getDigitalOceanClient().deleteKey(keyId);
        } else if (ObjectHelper.isNotEmpty(fingerprint)) {
            delete = getEndpoint().getDigitalOceanClient().deleteKey(fingerprint);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.KEY_FINGERPRINT + " must be specified");
        }

        LOG.trace("Delete Key {}", delete);
        exchange.getMessage().setBody(delete);
    }

};