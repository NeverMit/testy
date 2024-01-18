package collections;

import java.util.Comparator;

class UserComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        return user1.getBirthDate().compareTo(user2.getBirthDate());
    }
}
