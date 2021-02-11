//,temp,TxnLogProposalIterator.java,102,110,temp,FileTxnLog.java,353,361
//,3
public class xxx {
    public void close() {
        if(itr != null){
            try {
                itr.close();
            } catch (IOException ioe) {
                LOG.warn("Error closing file iterator", ioe);
            }
        }
    }

};