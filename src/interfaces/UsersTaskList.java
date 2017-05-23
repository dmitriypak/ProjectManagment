package interfaces;

import objects.UserTask;

/**
 * Created by HP on 23.05.2017.
 */
public interface UsersTaskList {
    void add(UserTask userTask);
    void delete(UserTask userTask);
    void update(UserTask userTask);
}
