//,temp,BeanIOErrorHandler.java,115,124,temp,BeanIOErrorHandler.java,104,113
//,2
public class xxx {
    @Override
    public void unexpectedRecord(UnexpectedRecordException ex) throws Exception {
        String msg = LOG_PREFIX + "UnexpectedRecord: " + ex.getMessage() + ": " + ex.getRecordContext().getRecordText();
        if (getConfiguration().isIgnoreUnexpectedRecords()) {
            LOG.debug(msg);
        } else {
            LOG.warn(msg);
            throw ex;
        }
    }

};