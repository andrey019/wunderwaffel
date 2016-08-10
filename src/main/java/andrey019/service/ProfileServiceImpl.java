package andrey019.service;


import andrey019.dao.UserDao;
import andrey019.model.JsonProfile;
import andrey019.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JsonProfile getProfile(String email) {
        JsonProfile jsonProfile = new JsonProfile();
        jsonProfile.setFromUser(userDao.getByEmail(email));
        return jsonProfile;
    }

    @Override
    public boolean updateProfile(String email, JsonProfile jsonProfile) {
        if ( (jsonProfile.getfName() == null) && (jsonProfile.getlName() == null) &&
                (jsonProfile.getPassword() == null) ) {
            return false;
        }
        User user = userDao.getByEmail(email);
        String encodedPassword = passCheckAndEncoding(jsonProfile.getPassword());
        if (encodedPassword != null) {
            // mail all (raw pass)
            jsonProfile.setPassword(encodedPassword);
        }
        // mail all

        user.setFromJsonProfile(jsonProfile);
        return userDao.save(user);
    }

    private String passCheckAndEncoding(String password) {
        if ( (password != null) && ( (password.length() > 6) && (password.length() < 20) ) ) {
            password = passwordEncoder.encode(password);
            return password;
        }
        return null;
    }
}
