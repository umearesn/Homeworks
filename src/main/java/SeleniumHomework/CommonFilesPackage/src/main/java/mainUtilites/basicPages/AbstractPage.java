package mainUtilites.basicPages;

import mainUtilites.urlChecker.URLPattern;
import mainUtilites.pageNavigation.Domain;
import mainUtilites.pageNavigation.PageURL;
import mainUtilites.wait.StandartWaiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPage<T> {

    protected WebDriver driver = null;
    protected StandartWaiter waiter = null;

    public AbstractPage(WebDriver driver){
        this.driver = driver;
        this.waiter = new StandartWaiter(driver);
        PageFactory.initElements(driver, this);
    }

    protected T open(){
        String adress = null;
        if(this.getClass().isAnnotationPresent(Domain.class)) {
            adress = this.getClass().getAnnotation(Domain.class).value();
        } else {
            adress = System.getProperty("webdriver.base.url");
        }
        if(this.getClass().isAnnotationPresent(PageURL.class)){
            adress = adress + this.getClass().getAnnotation(PageURL.class).value();
        }
        driver.get(adress);
        return (T) this;
    }

    protected T open(String urlTemplate, String... parameters) {
        try {
            String adress = null;
            if (this.getClass().isAnnotationPresent(Domain.class)) {
                adress = this.getClass().getAnnotation(Domain.class).value();
            } else {
                adress = System.getProperty("webdriver.base.url");
            }
            for (int i = 0; i < parameters.length; i++) {
                urlTemplate = urlTemplate.replace("%".concat(Integer.toString(i + 1)),
                        parameters[i]);
            }
            driver.get(adress + urlTemplate);
        } catch (Exception error){
            System.out.println("Domain or template is not given.");
        }
        return (T) this;
    }


    protected boolean isURLValid(){
        try{
            if( this.getClass().isAnnotationPresent(URLPattern.class) ) {
                Pattern urlPattern = Pattern.compile(this.getClass().getAnnotation(URLPattern.class).value());
                Matcher urlMatcher = urlPattern.matcher(driver.getCurrentUrl());
                return urlMatcher.matches();
            } else {
                throw new Exception();
            }
        } catch (Exception error){
            System.out.println("URL pattern is not given.");
            return false;
        }
    }

}
