package andrey019.service;

import org.springframework.stereotype.Service;

import java.util.Date;

//@Service("logService")
public class LogServiceImpl implements LogService {

    private StringBuilder stringBuilder;

    @Override
    public void accessToPage(String message) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(new Date());
        stringBuilder.append("] [Access to Page] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }
}
