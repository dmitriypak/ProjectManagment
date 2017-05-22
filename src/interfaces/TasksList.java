package interfaces;

import objects.Task;

/**
 * Created by HP on 21.05.2017.
 */
public interface TasksList {

    void add(Task task);
    void delete(Task task);
    void update(Task task);
}
