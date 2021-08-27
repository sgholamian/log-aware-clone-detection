//,temp,KahaDBIndexLocationTest.java,125,129,temp,MKahaDBIndexLocationTest.java,107,111
//,3
public class xxx {
            @Override
            public boolean accept(File dir, String name) {
                LOG.info("Testing index filename: {}", name);
                return name.endsWith("data") || name.endsWith("redo");
            }

};