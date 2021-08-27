//,temp,DefaultDatabaseLocker.java,44,119,temp,TransactDatabaseLocker.java,39,95
//,3
public class xxx {
    public void doStart() throws Exception {

        LOG.info("Attempting to acquire the exclusive lock to become the Master broker");
        String sql = getStatements().getLockCreateStatement();
        LOG.debug("Locking Query is "+sql);
        
        while (true) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                lockCreateStatement = connection.prepareStatement(sql);
                lockCreateStatement.execute();
                break;
            } catch (Exception e) {
                try {
                    if (isStopping()) {
                        throw new Exception(
                                "Cannot start broker as being asked to shut down. " 
                                        + "Interrupted attempt to acquire lock: "
                                        + e, e);
                    }
                    if (exceptionHandler != null) {
                        try {
                            exceptionHandler.handle(e);
                        } catch (Throwable handlerException) {
                            LOG.error( "The exception handler "
                                    + exceptionHandler.getClass().getCanonicalName()
                                    + " threw this exception: "
                                    + handlerException
                                    + " while trying to handle this exception: "
                                    + e, handlerException);
                        }

                    } else {
                        LOG.debug("Lock failure: "+ e, e);
                    }
                } finally {
                    // Let's make sure the database connection is properly
                    // closed when an error occurs so that we're not leaking
                    // connections 
                    if (null != connection) {
                        try {
                            connection.rollback();
                        } catch (SQLException e1) {
                            LOG.debug("Caught exception during rollback on connection: " + e1, e1);
                        }
                        try {
                            connection.close();
                        } catch (SQLException e1) {
                            LOG.debug("Caught exception while closing connection: " + e1, e1);
                        }
                        
                        connection = null;
                    }
                }
            } finally {
                if (null != lockCreateStatement) {
                    try {
                        lockCreateStatement.close();
                    } catch (SQLException e1) {
                        LOG.debug("Caught while closing statement: " + e1, e1);
                    }
                    lockCreateStatement = null;
                }
            }

            LOG.info("Failed to acquire lock.  Sleeping for " + lockAcquireSleepInterval + " milli(s) before trying again...");
            try {
                Thread.sleep(lockAcquireSleepInterval);
            } catch (InterruptedException ie) {
                LOG.warn("Master lock retry sleep interrupted", ie);
            }
        }

        LOG.info("Becoming the master on dataSource: " + dataSource);
    }

};