//,temp,ProjectDaoImpl.java,71,90,temp,ManagementServerHostDaoImpl.java,106,124
//,3
public class xxx {
    @Override
    @DB
    public boolean remove(Long projectId) {
        boolean result = false;
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        txn.start();
        ProjectVO projectToRemove = findById(projectId);
        projectToRemove.setName(null);
        if (!update(projectId, projectToRemove)) {
            s_logger.warn("Failed to reset name for the project id=" + projectId + " as a part of project remove");
            return false;
        }

        _tagsDao.removeByIdAndType(projectId, ResourceObjectType.Project);
        result = super.remove(projectId);
        txn.commit();

        return result;

    }

};