//,temp,KahaDBIndexLocationTest.java,116,120,temp,MKahaDBIndexLocationTest.java,116,120
//,3
public class xxx {
            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing log filename: {}", name);
                return name.endsWith("log") || name.equals("lock");
            }

};