//,temp,StAXXPathSplitChoicePerformanceTest.java,157,193,temp,TokenPairIteratorSplitChoicePerformanceTest.java,140,172
//,3
public class xxx {
    public static void createDataFile(Logger log, int size) throws Exception {
        deleteDirectory("target/data");
        createDirectory("target/data");

        log.info("Creating data file ...");

        File file = new File("target/data/data.xml");
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><orders>\n".getBytes());

        for (int i = 0; i < size; i++) {
            fos.write("<order>\n".getBytes());
            fos.write(("  <id>" + i + "</id>\n").getBytes());
            int num = i % 10;
            if (num >= 0 && num <= 3) {
                fos.write("  <amount>3</amount>\n".getBytes());
                fos.write("  <customerId>333</customerId>\n".getBytes());
            } else if (num >= 4 && num <= 5) {
                fos.write("  <amount>44</amount>\n".getBytes());
                fos.write("  <customerId>444</customerId>\n".getBytes());
            } else if (num >= 6 && num <= 8) {
                fos.write("  <amount>88</amount>\n".getBytes());
                fos.write("  <customerId>888</customerId>\n".getBytes());
            } else {
                fos.write("  <amount>123</amount>\n".getBytes());
                fos.write("  <customerId>123123</customerId>\n".getBytes());
            }
            fos.write("  <description>bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla</description>\n"
                    .getBytes());
            fos.write("</order>\n".getBytes());
        }

        fos.write("</orders>".getBytes());
        fos.close();

        log.info("Creating data file done.");
    }

};