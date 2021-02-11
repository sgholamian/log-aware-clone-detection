//,temp,ProjectDaoImpl.java,71,90,temp,ManagementServerHostDaoImpl.java,106,124
//,3
public class xxx {
    @Override
    @DB
    public boolean remove(Long id) {
        TransactionLegacy txn = TransactionLegacy.currentTxn();

        try {
            txn.start();

            ManagementServerHostVO msHost = findById(id);
            msHost.setState(ManagementServerHost.State.Down);
            super.remove(id);

            txn.commit();
            return true;
        } catch (Exception e) {
            s_logger.warn("Unexpected exception, ", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

};