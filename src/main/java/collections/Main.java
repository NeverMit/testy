package collections;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        TreeSet<User> userSet = new TreeSet<>(new UserComparator());
        userSet.add(new User("Alice", LocalDate.of(90, 10, 20)));
        userSet.add(new User("Bob", LocalDate.of(85, 10, 20)));
        userSet.add(new User("Charlie", LocalDate.of(95, 3, 8)));

        for (User user : userSet) {
            System.out.println(user.getName() + " " + user.getBirthDate());
        }
    }
    }
