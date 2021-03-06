import mainUtilites.basicPages.AbstractPage;
import mainUtilites.logger.Step;
import mainUtilites.pageNavigation.Domain;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

@Domain("https://google.com")
public class HelloWorldSearch extends AbstractPage<HelloWorldSearch> {

    @FindBy(xpath = "//input[@name='q']")
    private WebElement inputField;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='resultStats']")
    private WebElement resultsField;

    public HelloWorldSearch(WebDriver driver){
        super(driver);
    }

    public HelloWorldSearch open(){
        return super.open();
    }

    @Step("Sets text for the query.")
    public HelloWorldSearch setText(String query){
        inputField.sendKeys(query);
        return this;
    }

    public HelloWorldSearch sendQuery(){
        submitButton.click();
        return this;
    }

    public HelloWorldSearch resultsShoudBePresented() {
        assertTrue("Результаты не отображаются на странице",
                waiter.waitForCondition(ExpectedConditions
                        .textToBePresentInElement(resultsField, "Результатов")));
        return this;
    }

    public HelloWorldSearch moreThanZeroResults(){
        assertTrue("Результатов 0", Integer
                .parseInt(resultsField.getText().substring(22, 23)) > 0);
        return this;
    }

}
