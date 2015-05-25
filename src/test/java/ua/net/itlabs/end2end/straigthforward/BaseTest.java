package ua.net.itlabs.end2end.straigthforward;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;


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
 * Created by Maria Shmelyova on 09.04.15.
 */

public class BaseTest {

   ElementsCollection tasks = $$("#todo-list > li");
   Condition active = cssClass("active");
   Condition completed = cssClass("completed");
   SelenideElement clearCompletedButton = $("#clear-completed");
   SelenideElement newToDo = $("#new-todo");

   public void clearCompleted() {
      clearCompletedButton.click();
   }

   public void assertActiveTasksCount() {
      $("#todo-count").find("strong").shouldHave(exactText(Integer.toString(tasks.filter(active).size())));
   }
   public void assertCompletedTasksCount() {
      clearCompletedButton.shouldHave(exactText("Clear completed (" + Integer.toString(tasks.filter(completed).size()) + ")"));
   }
   public void toggleCompleted(SelenideElement task){
      task.find(".toggle").click();
   }

   public void addTask(String taskText){
      newToDo.val(taskText).pressEnter();
   }

   @Test
   public void testCreateTaskLifeCycle(){

      String task1 = "крякозяблики";
      String task2 = "akfjKKJJHBB,,JJSGJGBBXNKKO*IYT%*(*&^%%$$34567839944jrdfkjkjfkjfgkjfgkjgfkjfgkjfgkjfnvnmnmcjcdkjdfkfmjdjdfkj";
      String task3 = "!!!!!!!!";
      String task4 = "0";
      String taskCreateInActive = "active";
      String taskCreateInCompleted = "completed";

      open("http://todomvc.com/examples/troopjs_require/#/");

      //Create new task and check it

      for (String taskText : asList(task1, task2, task3, task4)) {
           newToDo.val(taskText).pressEnter();
      }


      tasks.filter(active).shouldHave(exactTexts(task1, task2, task3, task4));

      //Edit task

      doubleClick(tasks.get(0).find("label"));
      tasks.get(0).find(".edit").setValue("123").pressEnter();
      tasks.get(0).shouldHave(text(task1 = "123"));

      //Assert counters

      assertActiveTasksCount();
      clearCompletedButton.shouldBe(hidden); // new test

      //Assert Deletion task

      tasks.get(1).hover();
      tasks.get(1).find(".destroy").click();
      tasks.shouldHave(texts(task1, task3, task4));

      //Assert Toggle Completed

      toggleCompleted(tasks.get(2));
      tasks.filter(active).shouldHave(texts(task1, task3));
      tasks.filter(completed).shouldHave(texts(task4));

      assertCompletedTasksCount(); //new assert

      //Assert filters

      $("[href*='active']").click();
      assertActiveTasksCount();

      newToDo.setValue(taskCreateInActive).pressEnter(); //test  creation new task in active filter and then mark it as completed
      tasks.filter(active).shouldHave(texts(task1, task3, taskCreateInActive));
      toggleCompleted(tasks.findBy(text(taskCreateInActive)));
      tasks.findBy(text(taskCreateInActive)).shouldBe(hidden);

      assertEach(tasks.filter(completed), hidden);
      assertEach(tasks.filter(active), visible);

      $("[href*='completed']").click();
      assertActiveTasksCount();

      newToDo.setValue(taskCreateInCompleted).pressEnter(); //creation new task in completed filter
      tasks.findBy(text(taskCreateInCompleted)).shouldBe(hidden);

      assertEach(tasks.filter(active), hidden);
      assertEach(tasks.filter(completed), visible);

      $("[href='#/']").click();
      assertEach(tasks, visible);

      //Assert "Clear completed button"

      clearCompleted();
      tasks.filter(completed).shouldBe(empty);

      //Asset toggle all check-box

      $("#toggle-all").click();
      tasks.filter(completed).shouldHave(texts(task1, task3, taskCreateInCompleted));

      clearCompleted();
      tasks.shouldBe(empty);

      $("#filters").shouldBe(hidden); // new test
   }
}

