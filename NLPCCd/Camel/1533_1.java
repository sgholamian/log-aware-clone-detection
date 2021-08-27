//,temp,sample_3281.java,2,16,temp,sample_5558.java,2,16
//,3
public class xxx {
public void dummy_method(){
context.addRoutes(new RouteBuilder() {
public void configure() {
from("direct:start") .to(routeboxUri) .to("log:Routes operation performed?showAll=true");
}
});
context.start();
Book book = new Book("Sir Arthur Conan Doyle", "The Adventures of Sherlock Holmes");
String response = sendAddToCatalogRequest(template, "direct:start", "addToCatalog", book);
assertEquals("Book with Author " + book.getAuthor() + " and title " + book.getTitle() + " added to Catalog", response);
book = sendFindBookRequest(template, "direct:start", "findBook", "Sir Arthur Conan Doyle");


log.info("received book with author and title");
}

};