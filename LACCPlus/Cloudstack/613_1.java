//,temp,EngineClusterDaoImpl.java,258,307,temp,VMSnapshotDaoImpl.java,128,183
//,3
public class xxx {
    @Override
    public boolean updateState(State currentState, Event event, State nextState, DataCenterResourceEntity clusterEntity, Object data) {

        EngineClusterVO vo = findById(clusterEntity.getId());

        Date oldUpdatedTime = vo.getLastUpdated();

        SearchCriteria<EngineClusterVO> sc = StateChangeSearch.create();
        sc.setParameters("id", vo.getId());
        sc.setParameters("state", currentState);

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "lastUpdated", new Date());

        int rows = update(vo, sc);

        if (rows == 0 && s_logger.isDebugEnabled()) {
            EngineClusterVO dbCluster = findByIdIncludingRemoved(vo.getId());
            if (dbCluster != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=")
                    .append(dbCluster.getId())
                    .append("; state=")
                    .append(dbCluster.getState())
                    .append(";updatedTime=")
                    .append(dbCluster.getLastUpdated());
                str.append(": New Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(nextState)
                    .append("; event=")
                    .append(event)
                    .append("; updatedTime=")
                    .append(vo.getLastUpdated());
                str.append(": stale Data={id=")
                    .append(vo.getId())
                    .append("; state=")
                    .append(currentState)
                    .append("; event=")
                    .append(event)
                    .append("; updatedTime=")
                    .append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update dataCenter: id=" + vo.getId() + ", as there is no such dataCenter exists in the database anymore");
            }
        }
        return rows > 0;

    }

};