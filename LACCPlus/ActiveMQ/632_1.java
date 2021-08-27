//,temp,PageFileTest.java,497,503,temp,PageFileTest.java,354,360
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                pf2.flush();
                long freePages = pf2.getFreePageCount();
                LOG.info("free page count: " + freePages);
                return  recoveryEnd.get();
            }

};