package ua.net.itlabs.hw3;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static ua.net.itlabs.ConditionHelpers.assertEach;
import static ua.net.itlabs.hw3.ToDoMVCPage.*;


/**
 * Created by student on 20.05.15.
 */
public class TodoOperationsTest {

    @BeforeClass
    public static void openTodoMVCPage(){
        openTodoMVC();
    }

    @Before
    public static void clearData(){
        executeJavaScript("localStorage.clear()");
        open("http://todomvc.com");
        openTodoMVC();
    }

    @Test
    public void testAtAllFilter(){
        // create tasks
        addTask("1");
        addTask("2");
        addTask("3");
        addTask("4");
        tasks.filter(active).shouldHave(exactTexts("1", "2", "3", "4"));

        assertCounters();

        // edit task
        editTask("1", "1 edited");
        tasks.filter(active).shouldHave(exactTexts("1 edited", "2", "3", "4"));

        // delete task

        deleteTask("2");
        tasks.filter(active).shouldHave(exactTexts("1 edited", "3", "4"));

        // mark task completed

        toggleCompleted("3");
        tasks.filter(completed).shouldHave(exactTexts("3"));

        // filter tasks
        filteredActiveTasks();
        assertEach(tasks.filter(completed), hidden);
        assertEach(tasks.filter(active), visible);

        filteredCompletedTasks();
        assertEach(tasks.filter(completed), visible);
        assertEach(tasks.filter(active), hidden);

        // clear completed


        // reopen task

        // mark all tasks completed

        // clear (all) completed
    }

    @Test
    public void testAtActiveFilter(){

    }

    @Test
    public void testAtCompletedFilter(){

    }
}
