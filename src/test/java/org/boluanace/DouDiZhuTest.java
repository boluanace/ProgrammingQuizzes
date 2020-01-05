package org.boluanace;

import config.JavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
public class DouDiZhuTest {

    @Test
    public void getResult() {
        String string1 = "1 A\n2 A\n3 A\n4 A\n5 A\n";
        isEqual(1, string1, getDou());

        String string2 = "1 A\n1 B\n1 C\n1 D\n5 A\n";
        isEqual(2, string2, getDou());

        String string3 = "1 A\n1 B\n1 C\n2 A\n2 C\n";
        isEqual(3, string3, getDou());

        String string4 = "1 A\n3 A\n9 A\n2 A\n5 A\n";
        isEqual(4, string4, getDou());

        String string5 = "1 A\n4 B\n3 A\n2 A\n5 A\n";
        isEqual(5, string5, getDou());

        String string6 = "1 A\n1 B\n1 C\n2 A\n3 A\n";
        isEqual(6, string6, getDou());

        String string7 = "1 A\n9 B\n4 C\n2 A\n3 A\n";
        isEqual(7, string7, getDou());
    }

    public void isEqual(int code, String string, DouDiZhu dou) {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(string.getBytes()));
            dou.setInputStream(System.in);
            assertEquals(code, dou.getResult());
        }
        finally {
            System.setIn(stdin);

        }
    }

    public DouDiZhu getDou() {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        DouDiZhu dou = (DouDiZhu) context.getBean("DouDiZhu");
        return dou;
    }
}