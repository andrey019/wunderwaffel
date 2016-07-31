package andrey019.service;


import andrey019.dao.RegistrationDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfirmationCleanUpService extends Thread {

    private static ConfirmationCleanUpService CONFIRMATION_CLEAN_UP_SERVICE = new ConfirmationCleanUpService();
    private final static long INITIAL_DELAY = 10000;
    private final static long CLEANUP_AFTER = 86400000;
    private final static long CHECK_INTERVAL = 3600000;

    @Autowired
    private RegistrationDao registrationDao;

    private ConfirmationCleanUpService() {}

    @Override
    public void run() {
        try {
            Thread.sleep(INITIAL_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!isInterrupted()) {
            try {
                Thread.sleep(1000);//dfg
                System.out.println(registrationDao);
                registrationDao.deleteByDateOlderThen(System.currentTimeMillis() - CLEANUP_AFTER);
                Thread.sleep(CHECK_INTERVAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ConfirmationCleanUpService getInstance() {
        return CONFIRMATION_CLEAN_UP_SERVICE;
    }
}
