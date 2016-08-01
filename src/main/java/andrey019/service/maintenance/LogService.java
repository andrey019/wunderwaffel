package andrey019.service.maintenance;


public interface LogService {
    public void accessToPage(String message);
    public void mailSent(String message, int queued);
}
