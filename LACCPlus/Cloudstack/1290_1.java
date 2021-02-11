//,temp,ConsoleProxyAjaxHandler.java,43,66,temp,ConsoleProxyAjaxImageHandler.java,36,62
//,3
public class xxx {
    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            if (s_logger.isTraceEnabled())
                s_logger.trace("AjaxHandler " + t.getRequestURI());

            long startTick = System.currentTimeMillis();

            doHandle(t);

            if (s_logger.isTraceEnabled())
                s_logger.trace(t.getRequestURI() + " process time " + (System.currentTimeMillis() - startTick) + " ms");
        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            s_logger.warn("Exception, ", e);
            t.sendResponseHeaders(400, -1);     // bad request
        } catch (Throwable e) {
            s_logger.error("Unexpected exception, ", e);
            t.sendResponseHeaders(500, -1);     // server error
        } finally {
            t.close();
        }
    }

};