//,temp,PageFileTest.java,310,316,temp,PageFileTest.java,234,241
//,2
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                pf2.flush();
                long freePages = pf2.getFreePageCount();
                LOG.info("free page count: " + freePages);
                return  freePages == 199980;
            }

};