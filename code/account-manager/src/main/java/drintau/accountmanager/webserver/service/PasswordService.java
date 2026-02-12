package drintau.accountmanager.webserver.service;

public interface PasswordService {

    String getRandomPassword();

    String getRandomPassword(int digits);

}
