package ma.youcode.lineperm.models;
import java.util.*;

public class User {
    private String name;
    private String passwordHash;

public User setName(String name) {
    this.name = name;
    return this;
}
public User setPassword(String passwordHash) {
    this.passwordHash = passwordHash;
    return this;
}

public String getName() {
    return name;
}

public String getPassword() {
    return passwordHash;
}

    
}



