//,temp,LevelDBFile.java,206,215,temp,PulsarProducer.java,118,125
//,3
public class xxx {
    @Override
    public void stop() {
        File file = getFile();

        LOG.debug("Stopping LevelDB using file: {}", file);
        if (db != null) {
            IOHelper.close(db);
            db = null;
        }
    }

};