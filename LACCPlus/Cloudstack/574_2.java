//,temp,EngineDataCenterDaoImpl.java,292,336,temp,ObjectInDataStoreDaoImpl.java,54,109
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataObjectInStore dataObj, Object data) {
        ObjectInDataStoreVO vo = (ObjectInDataStoreVO)dataObj;
        Long oldUpdated = vo.getUpdatedCount();
        Date oldUpdatedTime = vo.getUpdated();

        SearchCriteria<ObjectInDataStoreVO> sc = updateStateSearch.create();
        sc.setParameters("id", vo.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", vo.getUpdatedCount());

        vo.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update(vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            ObjectInDataStoreVO dbVol = findByIdIncludingRemoved(vo.getId());
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
                    .append(vo.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(vo.getUpdatedCount())
                    .append("; updatedTime=")
                    .append(vo.getUpdated());
                str.append(": stale Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatecount=")
                    .append(oldUpdated)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update objectIndatastore: id=" + vo.getId() + ", as there is no such object exists in the database anymore");
            }
        }
        return rows > 0;
    }

};