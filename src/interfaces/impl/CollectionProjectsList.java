package interfaces.impl;

import interfaces.ProjectsList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Project;

/**
 * Created by HP on 21.05.2017.
 */
public class CollectionProjectsList implements ProjectsList{
    public static ObservableList<Project> projectsList = FXCollections.observableArrayList();

    @Override
    public void add(Project project) {
        projectsList.add(project);
    }

    @Override
    public void delete(Project project) {
        projectsList.remove(project);


    }
    @Override
    public void update(Project project) {

    }

    public void clearProjectsList(){
        projectsList.clear();
    }
    public ObservableList<Project> getProjectsListList() {
        return projectsList;
    }


}
