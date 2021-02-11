//,temp,TxnLogProposalIterator.java,102,110,temp,FileTxnLog.java,353,361
//,3
public class xxx {
    private void close(TxnIterator itr) {
        if (itr != null) {
            try {
                itr.close();
            } catch (IOException ioe) {
                LOG.warn("Error closing file iterator", ioe);
            }
        }
    }

};