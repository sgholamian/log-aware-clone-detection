//,temp,sample_6566.java,2,17,temp,sample_1582.java,2,17
//,3
public class xxx {
public void dummy_method(){
for (ParserResult result : list) {
}
Assert.assertEquals("direct:a", list.get(0).getElement());
Assert.assertEquals("direct:b", list.get(1).getElement());
Assert.assertEquals("direct:c", list.get(2).getElement());
Assert.assertEquals("direct:d", list.get(3).getElement());
Assert.assertEquals("direct:e", list.get(4).getElement());
Assert.assertEquals("direct:f", list.get(5).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
for (ParserResult result : list) {


log.info("producer");
}
}

};