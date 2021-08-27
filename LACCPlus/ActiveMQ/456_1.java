//,temp,MKahaDBStoreLimitTest.java,171,235,temp,MKahaDBStoreLimitTest.java,134,169
//,3
public class xxx {
    @Test
    public void testExplicitAdapterBlockingProducer() throws Exception {
        MultiKahaDBPersistenceAdapter persistenceAdapter = new MultiKahaDBPersistenceAdapter();
        KahaDBPersistenceAdapter kahaStore = new KahaDBPersistenceAdapter();
        kahaStore.setJournalMaxFileLength(1024*8);
        kahaStore.setIndexDirectory(new File(IOHelper.getDefaultDataDirectory()));

        FilteredKahaDBPersistenceAdapter filtered = new FilteredKahaDBPersistenceAdapter();
        StoreUsage storeUsage = new StoreUsage();
        storeUsage.setLimit(44*1024);

        filtered.setUsage(storeUsage);
        filtered.setDestination(queueA);
        filtered.setPersistenceAdapter(kahaStore);
        List<FilteredKahaDBPersistenceAdapter> stores = new ArrayList<>();
        stores.add(filtered);

        persistenceAdapter.setFilteredPersistenceAdapters(stores);

        BrokerService brokerService = createBroker(persistenceAdapter);
        brokerService.start();

        final AtomicBoolean done = new AtomicBoolean();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    produceMessages(queueA, 20);
                    done.set(true);
                } catch (Exception ignored) {
                }
            }
        });

        assertTrue("some messages got to dest", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                BaseDestination baseDestinationA = (BaseDestination) broker.getRegionBroker().getDestinationMap().get(queueA);
                return baseDestinationA != null && baseDestinationA.getDestinationStatistics().getMessages().getCount() > 4l;
            }
        }));

        BaseDestination baseDestinationA = (BaseDestination) broker.getRegionBroker().getDestinationMap().get(queueA);
        // loop till producer stalled
        long enqueues = 0l;
        do {
            enqueues = baseDestinationA.getDestinationStatistics().getEnqueues().getCount();
            LOG.info("Dest Enqueues: " + enqueues);
            TimeUnit.MILLISECONDS.sleep(500);
        } while (enqueues != baseDestinationA.getDestinationStatistics().getEnqueues().getCount());


        assertFalse("expect producer to block", done.get());

        LOG.info("Store global u: " + broker.getSystemUsage().getStoreUsage().getUsage() + ", %:" + broker.getSystemUsage().getStoreUsage().getPercentUsage());

        assertTrue("some usage", broker.getSystemUsage().getStoreUsage().getUsage() > 0);

        LOG.info("Store A u: " + baseDestinationA.getSystemUsage().getStoreUsage().getUsage() + ", %: " + baseDestinationA.getSystemUsage().getStoreUsage().getPercentUsage());

        assertTrue("limited store has more % usage than parent", baseDestinationA.getSystemUsage().getStoreUsage().getPercentUsage() > broker.getSystemUsage().getStoreUsage().getPercentUsage());

        executor.shutdownNow();
    }

};