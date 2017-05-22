package interfaces;

import objects.Project;

/**
 * Created by HP on 21.05.2017.
 */
public interface ProjectsList {

    void add(Project project);
    void delete(Project project);
    void update(Project project);
}
