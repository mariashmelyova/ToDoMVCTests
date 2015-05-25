package ua.net.itlabs.end2end.methods;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;
import static ua.net.itlabs.ConditionHelpers.*;

/**
 * Created by student on 23.04.15.
 */
public class ToDoMVCMethods {
    public static ElementsCollection tasks = $$("#todo-list > li");
    public static Condition active = cssClass("active");
    public static Condition completed = cssClass("completed");
    public static SelenideElement clearCompletedButton = $("#clear-completed");
    public static SelenideElement newToDo = $("#new-todo");

    public static void clearCompleted() {
        clearCompletedButton.click();
    }

    public static void deleteTask (SelenideElement element) {
        element.hover();
        element.find(".destroy").click();
    }
    public static void assertCounters(){
        if (tasks.isEmpty())
            $("#filters").shouldBe(hidden);
        else $("#todo-count").find("strong").shouldHave(exactText(Integer.toString(tasks.filter(active).size())));
        if (!tasks.filter(completed).isEmpty())
            clearCompletedButton.shouldHave(exactText("Clear completed (" + Integer.toString(tasks.filter(completed).size()) + ")"));
        else clearCompletedButton.shouldBe(hidden);
    }

    public static void assertActiveTasksCount(){
        $("#todo-count").shouldHave(text(Integer.toString(tasks.filter(active).size())));
    }

    public static void assertCompletedTasksCount(){
        clearCompletedButton.shouldHave(text(Integer.toString(tasks.filter(completed).size())));
    }

    public static void toggleCompleted(SelenideElement task) {
        task.find(".toggle").click();
    }

    public static void openTodoMVC(){
        Selenide.open("http://todomvc.com/examples/troopjs_require/#/");
    }

    public static String task1 = "крякозяблики";
    public static String task2 = "akfjKKJJHBB,,JJSGJGBBXNKKO*IYT%*(*&^%%$$34567839944jrdfkjkjfkjfgkjfgkjgfkjfgkjfgkjfnvnmnmcjcdkjdfkfmjdjdfkj";
    public static String task3 = "!!!!!!!!";
    public static String task4 = "0";
    public static String taskCreateInActive = "active";
    public static String taskCreateInCompleted = "completed";

    public static void createNewTasks(){
        for (String taskText : asList(task1, task2, task3, task4)) {
            newToDo.val(taskText).pressEnter();
        }
        tasks.shouldHave(exactTexts(task1, task2, task3, task4));
    }

    public static void assertEditingActiveTask() {
        doubleClick(tasks.get(0).find("label"));
        tasks.get(0).find(".edit").setValue("123").pressEnter();
        tasks.get(0).shouldHave(text(task1 = "123"));
    }
    public static void assertDeletionTask (){
        tasks.get(1).hover();
        tasks.get(1).find(".destroy").click();
        tasks.shouldHave(texts(task1, task3, task4));
    }

    public static void assertToggleCompleted(){
        toggleCompleted(tasks.get(2));
        tasks.filter(active).shouldHave(texts(task1, task3));
        tasks.filter(completed).shouldHave(texts(task4));
    }
    public static void assertActiveTasksFilter(){
        $("[href*='active']").click();
        newToDo.setValue(taskCreateInActive).pressEnter(); //test  creation new task in active filter and then mark it as completed
        tasks.filter(active).shouldHave(texts(task1, task3, taskCreateInActive));
        toggleCompleted(tasks.findBy(text(taskCreateInActive)));
        tasks.findBy(text(taskCreateInActive)).shouldBe(hidden);

        assertEach(tasks.filter(completed), hidden);
        assertEach(tasks.filter(active), visible);
    }

    public static void assertCompletedTasksFilter(){
        $("[href*='completed']").click();
        assertActiveTasksCount();
        newToDo.setValue(taskCreateInCompleted).pressEnter(); //creation new task in completed filter
        tasks.findBy(text(taskCreateInCompleted)).shouldBe(hidden);
        assertEach(tasks.filter(active), hidden);
        assertEach(tasks.filter(completed), visible);
    }

    public static void assertAllFilter(){
        $("[href='#/']").click();
        assertEach(tasks, visible);
    }

    public static void assertClearCompletedButton(){
        clearCompleted();
        tasks.filter(completed).shouldBe(empty);
    }

    public static void assertToggleAllCheckBox(){
        $("#toggle-all").click();
        tasks.filter(completed).shouldHave(texts(task1, task3, taskCreateInCompleted));
    }

    public static void assertClearAll(){
        clearCompleted();
        tasks.shouldBe(empty);
    }




}

