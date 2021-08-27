//,temp,sample_7251.java,2,11,temp,sample_7241.java,2,15
//,3
public class xxx {
protected int poll() throws Exception {
shutdownRunningTask = null;
pendingExchanges = 0;
if (entityManager == null) {
if (getEndpoint().isSharedEntityManager()) {
this.entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
} else {
this.entityManager = entityManagerFactory.createEntityManager();
}


log.info("recreated entitymanager on");
}
}

};