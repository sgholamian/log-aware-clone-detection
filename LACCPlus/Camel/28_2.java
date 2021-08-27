//,temp,DefaultValidationErrorHandler.java,58,64,temp,DefaultValidationErrorHandler.java,50,56
//,2
public class xxx {
    @Override
    public void error(SAXParseException e) throws SAXException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Validation error: {}", e, e);
        }
        errors.add(e);
    }

};