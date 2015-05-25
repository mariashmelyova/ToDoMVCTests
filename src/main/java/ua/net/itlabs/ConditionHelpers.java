package ua.net.itlabs;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.DoubleClickAction;
import static com.codeborne.selenide.Selenide.actions;


/**
 * Created by student on 14.04.15.
 */
public class ConditionHelpers {

    public static void assertEach(ElementsCollection elementsCollection, Condition condition){
        for (SelenideElement element : elementsCollection) {
            element.should(condition);
        }
    }

    public static void doubleClick(SelenideElement element) {
        actions().moveToElement(element.toWebElement()).doubleClick().build().perform();
    }

}
