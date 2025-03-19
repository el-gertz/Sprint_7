package praktikum.dto;

import java.util.concurrent.ThreadLocalRandom;

public class Courier {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String login;
    public String password;
    public String firstName;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier random() {
        int suffix = ThreadLocalRandom.current().nextInt(100, 100_000);
        return new Courier("Jack" + suffix, "P@ssw0rd123", "Sparrow");
    }


}
