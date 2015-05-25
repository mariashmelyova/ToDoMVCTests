package ua.net.itlabs.hw3;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static ua.net.itlabs.ConditionHelpers.doubleClick;

/*
* *
 * Created by student on 20.05.15.
 */
public class ToDoMVCPage {
    public static ElementsCollection tasks = $$("#todo-list > li");
    public static Condition active = cssClass("active");
    public static Condition completed = cssClass("completed");
    public static SelenideElement clearCompletedButton = $("#clear-completed");
    public static SelenideElement newToDo = $("#new-todo");

    @Step
    public static void openTodoMVC(){

        open("http://todomvc.com/examples/troopjs_require/#/");
    }
    @Step
    public static void addTask(String taskText) {
        newToDo.val(taskText).pressEnter();
    }

    @Step
    public static void toggleCompleted(String taskToMark){

        tasks.findBy(text(taskToMark)).find(".toggle").click();
    }

    @Step
    public static void clearCompleted() {

        clearCompletedButton.click();
    }

    @Step
    public static void assertCounters(){
        if (tasks.isEmpty())
            $("#filters").shouldBe(hidden);
        else $("#todo-count").find("strong").shouldHave(exactText(Integer.toString(tasks.filter(active).size())));
        if (!tasks.filter(completed).isEmpty())
            clearCompletedButton.shouldHave(exactText("Clear completed (" + Integer.toString(tasks.filter(completed).size()) + ")"));
        else clearCompletedButton.shouldBe(hidden);
    }

    @Step
    public static void deleteTask (String taskToDelete) {
        tasks.findBy(text(taskToDelete)).hover();
        tasks.findBy(text(taskToDelete)).find(".destroy").click();
    }

    @Step
    public static void editTask (String taskToEdit, String editedTask){
        doubleClick(tasks.get(0).find("label"));
        tasks.findBy(text(taskToEdit)).find(".edit").setValue(editedTask).pressEnter();
    }

    @Step
    public static void filteredActiveTasks() {
        $("[href*='active']").click();
    }

    @Step
    public static void filteredCompletedTasks() {
        $("[href*='completed']").click();
    }

    @Step
    public static void filteredAllTasks() {
        $("[href='#/']").click();;
    }


}
