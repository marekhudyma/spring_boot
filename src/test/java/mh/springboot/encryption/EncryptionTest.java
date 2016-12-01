package mh.springboot.encryption;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertTrue;

public class EncryptionTest {

    @Test
    public void bcryptTest() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("a", "$2a$10$39agsgfMsh9U8WsU66cC6.Dkm4x9R0SGJnVv1EJpU9V58JiN42jMC"));
        assertTrue(encoder.matches("u", "$2a$10$9B95rrd8IIPQ9l2wTSl5O.fGyqEyJnGhEGLf6OXw6jm0N.4FEJFdC"));
    }

    @Test
    public void bcryptGeneratePasswordTest() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("u"));
    }
}
