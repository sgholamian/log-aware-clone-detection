//,temp,VMSnapshotDaoImpl.java,128,183,temp,VolumeHostDaoImpl.java,124,179
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataObjectInStore vo, Object data) {
        VolumeHostVO volHost = (VolumeHostVO)vo;
        Long oldUpdated = volHost.getUpdatedCount();
        Date oldUpdatedTime = volHost.getUpdated();

        SearchCriteria<VolumeHostVO> sc = updateStateSearch.create();
        sc.setParameters("id", volHost.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", volHost.getUpdatedCount());

        volHost.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update((VolumeHostVO)vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            VolumeHostVO dbVol = findByIdIncludingRemoved(volHost.getId());
            if (dbVol != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=")
                    .append(dbVol.getId())
                    .append("; state=")
                    .append(dbVol.getState())
                    .append("; updatecount=")
                    .append(dbVol.getUpdatedCount())
                    .append(";updatedTime=")
                    .append(dbVol.getUpdated());
                str.append(": New Data={id=")
                    .append(volHost.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(volHost.getUpdatedCount())
                    .append("; updatedTime=")
                    .append(volHost.getUpdated());
                str.append(": stale Data={id=")
                    .append(volHost.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(oldUpdated)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update objectIndatastore: id=" + volHost.getId() + ", as there is no such object exists in the database anymore");
            }
        }
        return rows > 0;
    }

};