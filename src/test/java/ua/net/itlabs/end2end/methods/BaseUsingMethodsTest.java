package ua.net.itlabs.end2end.methods;

import org.junit.Test;


import static ua.net.itlabs.end2end.methods.ToDoMVCMethods.*;
/**
 * Created by student on 23.04.15.
 */
public class BaseUsingMethodsTest {

    @Test
    public void testUsingMethod(){
        openTodoMVC();

        createNewTasks();
        assertCounters();
        assertEditingActiveTask();
        assertDeletionTask();
        assertToggleCompleted();
        assertCounters();
        assertActiveTasksFilter();
        assertCounters();
        assertCompletedTasksFilter();
        assertCounters();
        assertAllFilter();
        assertClearCompletedButton();
        assertToggleAllCheckBox();
        assertClearAll();
    }

}
