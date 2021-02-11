//,temp,SnapshotDataStoreDaoImpl.java,164,219,temp,VMTemplateDaoImpl.java,1043,1102
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataObjectInStore vo, Object data) {
        SnapshotDataStoreVO dataObj = (SnapshotDataStoreVO)vo;
        Long oldUpdated = dataObj.getUpdatedCount();
        Date oldUpdatedTime = dataObj.getUpdated();

        SearchCriteria<SnapshotDataStoreVO> sc = updateStateSearch.create();
        sc.setParameters("id", dataObj.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", dataObj.getUpdatedCount());

        dataObj.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(dataObj);
        builder.set(dataObj, "state", nextState);
        builder.set(dataObj, "updated", new Date());

        int rows = update(dataObj, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            SnapshotDataStoreVO dbVol = findByIdIncludingRemoved(dataObj.getId());
            if (dbVol != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(dataObj.toString());
                str.append(": DB Data={id=")
                .append(dbVol.getId())
                .append("; state=")
                .append(dbVol.getState())
                .append("; updatecount=")
                .append(dbVol.getUpdatedCount())
                .append(";updatedTime=")
                .append(dbVol.getUpdated());
                str.append(": New Data={id=")
                .append(dataObj.getId())
                .append("; state=")
                .append(nextState)
                .append("; event=")
                .append(event)
                .append("; updatecount=")
                .append(dataObj.getUpdatedCount())
                .append("; updatedTime=")
                .append(dataObj.getUpdated());
                str.append(": stale Data={id=")
                .append(dataObj.getId())
                .append("; state=")
                .append(currentState)
                .append("; event=")
                .append(event)
                .append("; updatecount=")
                .append(oldUpdated)
                .append("; updatedTime=")
                .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update objectIndatastore: id=" + dataObj.getId() + ", as there is no such object exists in the database anymore");
            }
        }
        return rows > 0;
    }

};