//,temp,ConsoleProxyResourceHandler.java,62,82,temp,ConsoleProxyAjaxImageHandler.java,36,62
//,3
public class xxx {
    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            if (s_logger.isDebugEnabled())
                s_logger.debug("Resource Handler " + t.getRequestURI());

            long startTick = System.currentTimeMillis();

            doHandle(t);

            if (s_logger.isDebugEnabled())
                s_logger.debug(t.getRequestURI() + " Process time " + (System.currentTimeMillis() - startTick) + " ms");
        } catch (IOException e) {
            throw e;
        } catch (Throwable e) {
            s_logger.error("Unexpected exception, ", e);
            t.sendResponseHeaders(500, -1);     // server error
        } finally {
            t.close();
        }
    }

};