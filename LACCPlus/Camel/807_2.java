//,temp,StreamEndpoint.java,302,312,temp,MinaConsumer.java,341,354
//,3
public class xxx {
    private Charset getEncodingParameter(String type, MinaConfiguration configuration) {
        String encoding = configuration.getEncoding();
        if (encoding == null) {
            encoding = Charset.defaultCharset().name();
            // set in on configuration so its updated
            configuration.setEncoding(encoding);
            LOG.debug("{}: No encoding parameter using default charset: {}", type, encoding);
        }
        if (!Charset.isSupported(encoding)) {
            throw new IllegalArgumentException("The encoding: " + encoding + " is not supported");
        }

        return Charset.forName(encoding);
    }

};