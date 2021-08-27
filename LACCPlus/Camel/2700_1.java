//,temp,BeanIOErrorHandler.java,115,124,temp,BeanIOErrorHandler.java,104,113
//,2
public class xxx {
    @Override
    public void unidentifiedRecord(UnidentifiedRecordException ex) throws Exception {
        String msg = LOG_PREFIX + "UnidentifiedRecord: " + ex.getMessage() + ": " + ex.getRecordContext().getRecordText();
        if (getConfiguration().isIgnoreUnidentifiedRecords()) {
            LOG.debug(msg);
        } else {
            LOG.warn(msg);
            throw ex;
        }
    }

};