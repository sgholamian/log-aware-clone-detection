//,temp,LibvirtCapXMLParser.java,106,119,temp,LibvirtXMLParser.java,52,65
//,3
public class xxx {
    public String parseCapabilitiesXML(String capXML) {
        if (!_initialized) {
            return null;
        }
        try {
            _sp.parse(new InputSource(new StringReader(capXML)), this);
            return _capXML.toString();
        } catch (SAXException se) {
            s_logger.warn(se.getMessage());
        } catch (IOException ie) {
            s_logger.error(ie.getMessage());
        }
        return null;
    }

};