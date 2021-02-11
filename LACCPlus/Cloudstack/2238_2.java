//,temp,CitrixResourceBase.java,5166,5172,temp,SnapshotServiceImpl.java,570,578
//,3
public class xxx {
    @Override
    public void processEventOnSnapshotObject(SnapshotInfo snapshot, Snapshot.Event event) {
        SnapshotObject object = (SnapshotObject)snapshot;
        try {
            object.processEvent(event);
        } catch (NoTransitionException e) {
            s_logger.debug("Unable to update the state " + e.toString());
        }
    }

};