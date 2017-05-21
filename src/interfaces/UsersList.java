package interfaces;

import objects.User;

/**
 * Created by HP on 21.05.2017.
 */
public interface UsersList {

    void add(User user);
    void delete(User user);
    void update(User user);
}
