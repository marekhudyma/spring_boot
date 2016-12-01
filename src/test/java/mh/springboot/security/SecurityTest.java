package mh.springboot.security;

import mh.springboot.SpringBootMainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootMainApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testPage_anonymous() throws Exception {
        String url = String.format("http://localhost:%d/page.html", port);
        HttpClient httpClient = new HttpClient(url);
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Page"));
    }

    @Test
    public void testUserPage_anonymous() throws Exception {
        String url = String.format("http://localhost:%d/page_user.html", port);
        HttpClient httpClient = new HttpClient(url);
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Custom Login Page"));
    }

    @Test
    public void testUserPage_user() throws Exception {
        String url = String.format("http://localhost:%d/page_user.html", port);
        HttpClient httpClient = new HttpClient(url, "user", "u");
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Page for user"));
    }

    @Test
    public void testUserPage_admin() throws Exception {
        String url = String.format("http://localhost:%d/page_user.html", port);
        HttpClient httpClient = new HttpClient(url, "admin", "a");
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Page for user"));
    }

    @Test
    public void testAdminPage_anonymous() throws Exception {
        String url = String.format("http://localhost:%d/page_admin.html", port);
        HttpClient httpClient = new HttpClient(url);
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Custom Login Page"));
    }

    @Test
    public void testAdminPage_user() throws Exception {
        String url = String.format("http://localhost:%d/page_admin.html", port);
        HttpClient httpClient = new HttpClient(url, "user", "u");
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Forbidden"));
    }

    @Test
    public void testAdminPage_admin() throws Exception {
        String url = String.format("http://localhost:%d/page_admin.html", port);
        HttpClient httpClient = new HttpClient(url, "admin", "a");
        HttpClient.Result result = httpClient.execute();

        assertEquals(200, result.getResponseCode());
        assertTrue(result.getHtml().contains("Page for admin"));
    }

}
