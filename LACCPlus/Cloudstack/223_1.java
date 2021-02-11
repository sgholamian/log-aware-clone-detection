//,temp,MessageBusBase.java,315,339,temp,SynchronizationEvent.java,50,66
//,3
public class xxx {
        public boolean enter(boolean wait) {
            while (true) {
                synchronized (this) {
                    if (_reentranceCount == 0) {
                        assert (_gateOwner == null);

                        _reentranceCount++;
                        _gateOwner = Thread.currentThread();
                        return true;
                    } else {
                        if (wait) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                s_logger.debug("[ignored] interupted while guarding re-entrance on message bus.");
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

            return false;
        }

};