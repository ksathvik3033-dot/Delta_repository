
package com.automation.stepdefinitions;

import com.automation.hooks.Hooks;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;

public class AmazonSignInSteps {

    private WebDriverWait wait;

    @Given("I open the Amazon homepage")
    public void i_open_the_amazon_homepage() {

        Hooks.driver = new org.openqa.selenium.chrome.ChromeDriver();

        Hooks.driver.manage().window().maximize();

        wait = new WebDriverWait(
                Hooks.driver,
                Duration.ofSeconds(20)
        );

        Hooks.driver.get("https://www.amazon.com");
    }

    @When("I click the Sign in button")
    public void i_click_the_sign_in_button() {

        WebElement signInButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("nav-link-accountList")
                )
        );

        signInButton.click();
    }

    @Then("I should see the Amazon sign in page")
    public void i_should_see_the_amazon_sign_in_page() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("ap_email_login")
                )
        );

        Assert.assertTrue(
                "Amazon sign-in page was not displayed",
                Hooks.driver.getCurrentUrl().contains("signin")
                        || !Hooks.driver.findElements(
                        By.id("ap_email_login")
                ).isEmpty()
        );
    }

    // Stepdefinitions for wireless headphones

        @Given("I open Amazon for product details")
        public void i_open_amazon_for_product_details() {

            Hooks.driver = new org.openqa.selenium.chrome.ChromeDriver();

            Hooks.driver.manage().window().maximize();

            wait = new WebDriverWait(
                    Hooks.driver,
                    Duration.ofSeconds(20)
            );

            Hooks.driver.get("https://www.amazon.com");
        }

        @When("I search for {string}")
        public void i_search_for_product(String productName) {

            WebElement searchBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("twotabsearchtextbox")
                    )
            );

            searchBox.sendKeys(productName);
            searchBox.submit();
        }

//        @And("I click the first product")
//        public void i_click_the_first_product() {
//
//            By firstProduct = By.cssSelector(
//                    "div[data-component-type='s-search-result']"
//            );
//
//            wait.until(
//                    ExpectedConditions.elementToBeClickable(firstProduct)
//            ).click();
//        }


    @And("I click the first product")
    public void i_click_the_first_product() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(30));

        System.out.println("Current URL: " + Hooks.driver.getCurrentUrl());
        System.out.println("Page title: " + Hooks.driver.getTitle());

        // Wait for the page to finish loading
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Try multiple possible product-link selectors
        By[] selectors = {
                By.cssSelector("div[data-component-type='s-search-result'] h2 a"),
                By.cssSelector("[data-component-type='s-search-result'] a[href*='/dp/']"),
                By.cssSelector("a[href*='/dp/']"),
                By.cssSelector("a[href*='/gp/aw/d/']")
        };

        WebElement product = null;

        for (By selector : selectors) {
            List<WebElement> links = Hooks.driver.findElements(selector);

            System.out.println(
                    "Selector: " + selector +
                            " | Links found: " + links.size()
            );

            for (WebElement link : links) {
                if (link.isDisplayed() && link.isEnabled()) {
                    product = link;
                    break;
                }
            }

            if (product != null) {
                break;
            }
        }

        if (product == null) {
            System.out.println("No usable product link was found.");
            System.out.println(
                    "Page contains 'captcha': " +
                            Hooks.driver.getPageSource().toLowerCase().contains("captcha")
            );

            throw new RuntimeException(
                    "No usable product link found. Check the Amazon page and selectors."
            );
        }

        System.out.println("Clicking product: " + product.getAttribute("href"));

        wait.until(ExpectedConditions.elementToBeClickable(product)).click();
    }




    @Then("I should see the product details page")
        public void i_should_see_the_product_details_page() {

            wait.until(
                    ExpectedConditions.or(
                            ExpectedConditions.urlContains("/dp/"),
                            ExpectedConditions.urlContains("/gp/")
                    )
            );

            List<WebElement> productTitles = Hooks.driver.findElements(
                    By.id("productTitle")
            );

            Assert.assertFalse(
                    "Product details page was not displayed",
                    productTitles.isEmpty()
            );

            Assert.assertTrue(
                    "Product title is empty",
                    !productTitles.get(0).getText().trim().isEmpty()
            );

            System.out.println(
                    "Product opened: " + productTitles.get(0).getText()
            );
        }



        // stepdefinitions for shoes

    }

