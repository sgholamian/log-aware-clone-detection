//,temp,TestUtils.java,71,91,temp,TestUtils.java,43,64
//,3
public class xxx {
    public static <T> boolean waitFor(Predicate<T> resourceCheck, T payload) {
        boolean state = false;
        int retries = 30;
        int waitTime = 1000;
        do {
            try {
                state = resourceCheck.test(payload);

                if (!state) {
                    LOG.debug("The resource is not yet available. Waiting {} seconds before retrying",
                            TimeUnit.MILLISECONDS.toSeconds(waitTime));
                    retries--;
                    Thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                break;
            }

        } while (!state && retries > 0);

        return state;
    }

};