//,temp,BindyComplexKeyValuePairStandaloneTest.java,92,109,temp,BindyComplexKeyValuePairStandaloneTest.java,72,90
//,3
public class xxx {
    @Test
    public void testSeveralGroupMessage() throws Exception {

        String message = "8=FIX 4.19=2034=135=049=INVMGR56=BRKR"
                         + "1=BE.CHM.00111=CHM0001-0158=this is a camel - bindy test" + "22=448=BE000124567854=1"
                         + "22=548=BE000987654354=2" + "22=648=BE000999999954=3" + "10=220"
                         + "777=22-06-2013 12:21:11";

        List<String> data = Arrays.asList(message.split("\\u0001"));

        CamelContext camelContext = new DefaultCamelContext();
        factory.bind(camelContext, data, model, counter);

        LOG.info(">>> Model : " + model.toString());

        assertNotNull(model);

    }

};