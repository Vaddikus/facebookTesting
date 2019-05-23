package ui;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uiPosting.WebDriverPosting;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostTesting {

    WebDriverPosting webDriverPosting = new WebDriverPosting();
    @Test()
    @Order(1)
    public void testCreate(){
        webDriverPosting.createPost();
    }
}
