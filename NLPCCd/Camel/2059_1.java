//,temp,sample_4815.java,2,16,temp,sample_8445.java,2,16
//,2
public class xxx {
public void dummy_method(){
context.addRoutes(new RouteBuilder() {
public void configure() {
from(routeboxUri) .to("log:Routes operation performed?showAll=true");
}
});
context.start();
Book book = new Book("Sir Arthur Conan Doyle", "The Adventures of Sherlock Holmes");
String response = sendAddToCatalogRequest(template, routeboxUri, "addToCatalog", book);
assertEquals("Book with Author " + book.getAuthor() + " and title " + book.getTitle() + " added to Catalog", response);
book = sendFindBookRequest(template, routeboxUri, "findBook", "Sir Arthur Conan Doyle");


log.info("received book with author and title");
}

};