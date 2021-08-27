//,temp,MKahaDBStoreLimitTest.java,171,235,temp,MKahaDBStoreLimitTest.java,134,169
//,3
public class xxx {
    @Test
    public void testExplicitAdapter() throws Exception {
        MultiKahaDBPersistenceAdapter persistenceAdapter = new MultiKahaDBPersistenceAdapter();
        KahaDBPersistenceAdapter kahaStore = new KahaDBPersistenceAdapter();
        kahaStore.setJournalMaxFileLength(1024*25);

        FilteredKahaDBPersistenceAdapter filtered = new FilteredKahaDBPersistenceAdapter();
        StoreUsage storeUsage = new StoreUsage();
        storeUsage.setPercentLimit(50);
        storeUsage.setTotal(512*1024);

        filtered.setUsage(storeUsage);
        filtered.setDestination(queueA);
        filtered.setPersistenceAdapter(kahaStore);
        List<FilteredKahaDBPersistenceAdapter> stores = new ArrayList<>();
        stores.add(filtered);

        persistenceAdapter.setFilteredPersistenceAdapters(stores);

        BrokerService brokerService = createBroker(persistenceAdapter);
        brokerService.getSystemUsage().getStoreUsage().setTotal(1024*1024);
        brokerService.start();


        produceMessages(queueA, 20);

        LOG.info("Store global u: " + broker.getSystemUsage().getStoreUsage().getUsage() + ", %:" + broker.getSystemUsage().getStoreUsage().getPercentUsage());

        assertTrue("some usage", broker.getSystemUsage().getStoreUsage().getUsage() > 0);

        BaseDestination baseDestinationA = (BaseDestination) broker.getRegionBroker().getDestinationMap().get(queueA);
        LOG.info("Store A u: " + baseDestinationA.getSystemUsage().getStoreUsage().getUsage() + ", %: " + baseDestinationA.getSystemUsage().getStoreUsage().getPercentUsage());

        assertTrue("limited store has more % usage than parent", baseDestinationA.getSystemUsage().getStoreUsage().getPercentUsage() > broker.getSystemUsage().getStoreUsage().getPercentUsage());

    }

};