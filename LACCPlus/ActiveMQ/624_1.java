//,temp,JDBCPersistenceAdapter.java,673,682,temp,JDBCPersistenceAdapter.java,446,457
//,3
public class xxx {
    @Override
    public Locker createDefaultLocker() throws IOException {
        Locker locker = (Locker) loadAdapter(lockFactoryFinder, "lock");
        if (locker == null) {
            locker = new DefaultDatabaseLocker();
            LOG.debug("Using default JDBC Locker: " + locker);
        }
        locker.configure(this);
        return locker;
    }

};