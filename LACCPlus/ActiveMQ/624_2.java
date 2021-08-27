//,temp,JDBCPersistenceAdapter.java,673,682,temp,JDBCPersistenceAdapter.java,446,457
//,3
public class xxx {
    protected JDBCAdapter createAdapter() throws IOException {

        adapter = (JDBCAdapter) loadAdapter(adapterFactoryFinder, "adapter");

        // Use the default JDBC adapter if the
        // Database type is not recognized.
        if (adapter == null) {
            adapter = new DefaultJDBCAdapter();
            LOG.debug("Using default JDBC Adapter: " + adapter);
        }
        return adapter;
    }

};