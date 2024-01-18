package collections;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private String name;
    private LocalDate birthDate;

    public User(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }


}
