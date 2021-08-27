//,temp,PageFileTest.java,327,362,temp,PageFileTest.java,271,325
//,3
public class xxx {
    public void testBackgroundRecoveryIsThreadSafe() throws Exception {

        PageFile pf = new PageFile(new File("target/test-data"), getName());
        pf.delete();
        pf.setEnableRecoveryFile(false);
        pf.load();

        Transaction tx = pf.tx();
        tx.allocate(100000);
        tx.commit();
        LOG.info("Number of free pages:" + pf.getFreePageCount());
        pf.flush();

        //Simulate an unclean shutdown
        final PageFile pf2 = new PageFile(new File("target/test-data"), getName());
        pf2.setEnableRecoveryFile(false);
        pf2.load();

        Transaction tx2 = pf2.tx();
        tx2.allocate(100000);
        tx2.commit();
        LOG.info("Number of free pages:" + pf2.getFreePageCount());

        List<Transaction> transactions = new LinkedList<>();

        Thread.sleep(500);
        LOG.info("Creating Transactions");
        for (int i = 0; i < 20; i++) {
            Transaction txConcurrent = pf2.tx();
            Page page = txConcurrent.allocate();
            String t = "page:" + i;
            page.set(t);
            txConcurrent.store(page, StringMarshaller.INSTANCE, false);
            txConcurrent.commit();
            transactions.add(txConcurrent);
            Thread.sleep(50);
        }

        assertTrue("We have 199980 free pages", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                pf2.flush();
                long freePages = pf2.getFreePageCount();
                LOG.info("free page count: " + freePages);
                return  freePages == 199980;
            }
        }, 12000000));

        for (Transaction txConcurrent2: transactions) {
            for (Page page : txConcurrent2) {
                assertFalse(pf2.isFreePage(page.pageId));
            }
        }

    }

};