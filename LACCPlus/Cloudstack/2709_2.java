//,temp,VMTemplateHostDaoImpl.java,374,429,temp,VolumeDaoImpl.java,505,538
//,3
public class xxx {
    @Override
    public boolean updateState(com.cloud.storage.Volume.State currentState, Event event, com.cloud.storage.Volume.State nextState, Volume vo, Object data) {

        Long oldUpdated = vo.getUpdatedCount();
        Date oldUpdatedTime = vo.getUpdated();

        SearchCriteria<VolumeVO> sc = AllFieldsSearch.create();
        sc.setParameters("id", vo.getId());
        sc.setParameters("state", currentState);
        sc.setParameters("updatedCount", vo.getUpdatedCount());

        vo.incrUpdatedCount();

        UpdateBuilder builder = getUpdateBuilder(vo);
        builder.set(vo, "state", nextState);
        builder.set(vo, "updated", new Date());

        int rows = update((VolumeVO)vo, sc);
        if (rows == 0 && s_logger.isDebugEnabled()) {
            VolumeVO dbVol = findByIdIncludingRemoved(vo.getId());
            if (dbVol != null) {
                StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                str.append(": DB Data={id=").append(dbVol.getId()).append("; state=").append(dbVol.getState()).append("; updatecount=").append(dbVol.getUpdatedCount()).append(";updatedTime=")
                .append(dbVol.getUpdated());
                str.append(": New Data={id=").append(vo.getId()).append("; state=").append(nextState).append("; event=").append(event).append("; updatecount=").append(vo.getUpdatedCount())
                .append("; updatedTime=").append(vo.getUpdated());
                str.append(": stale Data={id=").append(vo.getId()).append("; state=").append(currentState).append("; event=").append(event).append("; updatecount=").append(oldUpdated)
                .append("; updatedTime=").append(oldUpdatedTime);
            } else {
                s_logger.debug("Unable to update volume: id=" + vo.getId() + ", as there is no such volume exists in the database anymore");
            }
        }
        return rows > 0;
    }

};