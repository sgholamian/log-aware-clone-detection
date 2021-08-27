//,temp,DigitalOceanDropletsProducer.java,437,447,temp,DigitalOceanKeysProducer.java,103,127
//,3
public class xxx {
    private void createKey(Exchange exchange) throws Exception {
        Key key = new Key();

        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");

        } else {
            key.setName(name);
        }

        String publicKey = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_PUBLIC_KEY, String.class);

        if (ObjectHelper.isEmpty(publicKey)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.KEY_PUBLIC_KEY + " must be specified");
        } else {
            key.setPublicKey(publicKey);
        }

        key = getEndpoint().getDigitalOceanClient().createKey(key);
        LOG.trace("Key created {}", key);
        exchange.getMessage().setBody(key);

    }

};