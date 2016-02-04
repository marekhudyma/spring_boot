package mh.springboot.security;

import mh.springboot.SpringBootMainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
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
        assertTrue(result.getHtml().contains("Login Page"));
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
        assertTrue(result.getHtml().contains("Login Page"));
    }

    @Test
    public void testAdminPage_user() throws Exception {
        String url = String.format("http://localhost:%d/page_admin.html", port);
        HttpClient httpClient = new HttpClient(url, "user", "u");
        HttpClient.Result result = httpClient.execute();

        assertEquals(403, result.getResponseCode());
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
