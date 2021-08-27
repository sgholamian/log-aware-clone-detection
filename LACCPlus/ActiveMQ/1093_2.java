//,temp,DefaultDatabaseLocker.java,44,119,temp,TransactDatabaseLocker.java,39,95
//,3
public class xxx {
    @Override
    public void doStart() throws Exception {

        LOG.info("Attempting to acquire the exclusive lock to become the Master broker");
        PreparedStatement statement = null;
        while (true) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                String sql = getStatements().getLockCreateStatement();
                statement = connection.prepareStatement(sql);
                if (statement.getMetaData() != null) {
                    ResultSet rs = statement.executeQuery();
                    // if not already locked the statement below blocks until lock acquired
                    rs.next();
                } else {
                    statement.execute();
                }
                break;
            } catch (Exception e) {
                if (isStopping()) {
                    throw new Exception("Cannot start broker as being asked to shut down. Interrupted attempt to acquire lock: " + e, e);
                }

                if (exceptionHandler != null) {
                    try {
                        exceptionHandler.handle(e);
                    } catch (Throwable handlerException) {
                        LOG.error("The exception handler " + exceptionHandler.getClass().getCanonicalName() + " threw this exception: " + handlerException
                                + " while trying to handle this excpetion: " + e, handlerException);
                    }

                } else {
                    LOG.error("Failed to acquire lock: " + e, e);
                }
            } finally {

                if (null != statement) {
                    try {
                        statement.close();
                    } catch (SQLException e1) {
                        LOG.warn("Caught while closing statement: " + e1, e1);
                    }
                    statement = null;
                }
            }

            LOG.debug("Sleeping for " + lockAcquireSleepInterval + " milli(s) before trying again to get the lock...");
            try {
            	Thread.sleep(lockAcquireSleepInterval);
            } catch (InterruptedException ie) {
            	LOG.warn("Master lock retry sleep interrupted", ie);
            }
        }

        LOG.info("Becoming the master on dataSource: " + dataSource);
    }

};