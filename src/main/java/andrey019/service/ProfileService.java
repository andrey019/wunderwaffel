package andrey019.service;


import andrey019.model.JsonProfile;

public interface ProfileService {

    JsonProfile getProfile(String email);
    String updateProfile(String email, JsonProfile jsonProfile);
}
