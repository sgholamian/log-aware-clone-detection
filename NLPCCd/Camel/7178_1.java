//,temp,sample_7251.java,2,11,temp,sample_7241.java,2,15
//,3
public class xxx {
protected void doStart() throws Exception {
if (getEndpoint().isSharedEntityManager()) {
this.entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
} else {
this.entityManager = entityManagerFactory.createEntityManager();
}


log.info("created entitymanager on");
}

};