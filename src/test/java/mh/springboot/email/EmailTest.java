package mh.springboot.email;

import mh.springboot.SpringBootMainApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore("because it was classified as spam")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootMainApplication.class)
public class EmailTest {

    @Autowired
    EmailSender emailSender;

    @Value("${email.to}")
    private String emailTo;

    @Test
    public void testEmailSender_sendSimpleEmail() throws Exception {
        emailSender.sendSimpleEmail(
                emailTo,
                "subject",
                "text body"
        );
    }

    @Test
    public void testEmailSender_sendHtmlEmail() throws Exception {
        String text =
                "<html>" +
                "<body>" +
                "<h2>Hello User</h2>" +
                "<p>You have registered in our system.</p>" +
                "<p><img src='http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2010/01/google_logo5.jpg' " +
                "width='100' alt='logo'></p>" +
                "</body>" +
                "</html>";
        emailSender.sendHtmlEmail(
                emailTo,
                "subject",
                text
        );
    }

}
