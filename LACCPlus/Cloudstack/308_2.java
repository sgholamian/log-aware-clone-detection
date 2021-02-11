//,temp,LibvirtCapXMLParser.java,106,119,temp,LibvirtXMLParser.java,52,65
//,3
public class xxx {
    public boolean parseDomainXML(String domXML) {
        if (!_initialized) {
            return false;
        }
        try {
            _sp.parse(new InputSource(new StringReader(domXML)), this);
            return true;
        } catch (SAXException se) {
            s_logger.warn(se.getMessage());
        } catch (IOException ie) {
            s_logger.error(ie.getMessage());
        }
        return false;
    }

};