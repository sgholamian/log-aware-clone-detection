//,temp,PageFileTest.java,327,362,temp,PageFileTest.java,271,325
//,3
public class xxx {
    public void testBackgroundWillNotMarkEaslyPagesAsFree() throws Exception {

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
        tx2.allocate(200);
        tx2.commit();
        LOG.info("Number of free pages:" + pf2.getFreePageCount());

        Transaction tx3 = pf2.tx();
        tx3.allocate(100);

        assertTrue("We have 10 free pages", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                pf2.flush();
                long freePages = pf2.getFreePageCount();
                LOG.info("free page count: " + freePages);
                return  freePages == 100100;
            }
        }, 12000000));
    }

};