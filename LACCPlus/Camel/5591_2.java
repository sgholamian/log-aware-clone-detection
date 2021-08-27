//,temp,SplunkHECConfiguration.java,81,91,temp,CxfEndpointUtils.java,45,55
//,3
public class xxx {
    public static QName getQName(final String name) {
        QName qName = null;
        if (name != null) {
            try {
                qName = QName.valueOf(name);
            } catch (Exception ex) {
                LOG.warn("Cannot create QName: {}", ex.getMessage(), ex);
            }
        }
        return qName;
    }

};