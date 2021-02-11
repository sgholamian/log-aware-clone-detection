//,temp,DatabaseUpgradeChecker.java,193,206,temp,PropertiesStorage.java,68,80
//,3
public class xxx {
    protected void runScript(Connection conn, InputStream file) {

        try (InputStreamReader reader = new InputStreamReader(file)) {
            ScriptRunner runner = new ScriptRunner(conn, false, true);
            runner.runScript(reader);
        } catch (IOException e) {
            s_logger.error("Unable to read upgrade script", e);
            throw new CloudRuntimeException("Unable to read upgrade script", e);
        } catch (SQLException e) {
            s_logger.error("Unable to execute upgrade script", e);
            throw new CloudRuntimeException("Unable to execute upgrade script", e);
        }

    }

};