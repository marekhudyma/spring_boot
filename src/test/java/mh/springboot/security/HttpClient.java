package mh.springboot.security;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

public class HttpClient {

    private final String url;
    private final boolean needToLogin;
    private final String login;
    private final String password;

    public HttpClient(String url) {
        this(url, false, null, null);
    }

    public HttpClient(String url, String login, String password) {
        this(url, true, login, password);
    }

    private HttpClient(String url, boolean needToLogin, String login, String password) {
        this.url = url;
        this.needToLogin = needToLogin;
        this.login = login;
        this.password = password;
    }

    class Result {
        private int responseCode;
        private String html;

        public Result(int responseCode, String html) {
            this.responseCode = responseCode;
            this.html = html;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getHtml() {
            return html;
        }
    }

    public Result execute() throws IOException {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page1 = webClient.getPage(url);

            if(!needToLogin) {
                return new Result(200, page1.asXml());
            } else {
                HtmlForm form = page1.getFormByName("f");
                HtmlTextInput usernameElement = form.getInputByName("username");
                HtmlPasswordInput passwordElement = form.getInputByName("password");
                HtmlSubmitInput buttonElement = form.getInputByName("submit");
                usernameElement.setValueAttribute(login);
                passwordElement.setValueAttribute(password);
                HtmlPage page2 = buttonElement.click();
                return new Result(200, page2.asXml());
            }
        } catch (FailingHttpStatusCodeException e) {
            return new Result(e.getStatusCode(), e.getStatusMessage());
        }
    }
}
