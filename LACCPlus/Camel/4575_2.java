//,temp,StAXXPathSplitChoicePerformanceTest.java,157,193,temp,TokenPairIteratorSplitChoicePerformanceTest.java,140,172
//,3
public class xxx {
    public void createDataFile(Logger log, int size) throws Exception {
        log.info("Creating data file ...");

        try (OutputStream fos = Files.newOutputStream(testFile("data.xml"))) {
            fos.write("<orders>\n".getBytes());

            for (int i = 0; i < size; i++) {
                fos.write("<order>\n".getBytes());
                fos.write(("  <id>" + i + "</id>\n").getBytes());
                int num = i % 10;
                if (num <= 3) {
                    fos.write("  <amount>3</amount>\n".getBytes());
                    fos.write("  <customerId>333</customerId>\n".getBytes());
                } else if (num <= 5) {
                    fos.write("  <amount>44</amount>\n".getBytes());
                    fos.write("  <customerId>444</customerId>\n".getBytes());
                } else if (num <= 8) {
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
        }

        log.info("Creating data file done.");
    }

};