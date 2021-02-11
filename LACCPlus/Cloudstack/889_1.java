//,temp,PropertiesStorage.java,68,80,temp,SspGuestNetworkGuru.java,131,143
//,3
public class xxx {
    private synchronized boolean loadFromFile(final File file) {
        try {
            PropertiesUtil.loadFromFile(_properties, file);
            _file = file;
        } catch (FileNotFoundException e) {
            s_logger.error("How did we get here? ", e);
            return false;
        } catch (IOException e) {
            s_logger.error("IOException: ", e);
            return false;
        }
        return true;
    }

};