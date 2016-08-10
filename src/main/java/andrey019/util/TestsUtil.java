package andrey019.util;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;


public class TestsUtil {
//    private final static ST TEXT_TEMPLATE = new ST("You were trying to register an account on WunderWaffel," +
//            "to confirm please click on the link below...\r\n" +
//            "http://wunderwaffel-andrey019.rhcloud.com/confirm?<code>");
//    {TEXT_TEMPLATE.add("code", "init");}

    private final static String TEXT_TEMPLATE = "You were trying to register an account on WunderWaffel," +
            "to confirm please click on the link below...\r\n" +
            "http://wunderwaffel-andrey019.rhcloud.com/confirm?%s";


    public static void main(String[] args) {
//        System.out.println(TEXT_TEMPLATE.getAttribute("code"));
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.remove("code");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.add("code", "11111");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.add("code", "22222");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.remove("code");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.remove("code");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.remove("code");
//        System.out.println(TEXT_TEMPLATE.render());
//        TEXT_TEMPLATE.add("code", "55555");
//        System.out.println(TEXT_TEMPLATE.render());

//        String result = String.format(TEXT_TEMPLATE, "1111");
//        System.out.println(result);
//        String result2 = String.format(TEXT_TEMPLATE, "2222");
//        System.out.println(result2);
//        String result3 = String.format(TEXT_TEMPLATE, "3333");
//        System.out.println(result3);
//
//        String str = "ololo";
//        String result4 = str + System.currentTimeMillis();
//        System.out.println(result4);
//
//        System.out.println(Charset.defaultCharset());

        EmailValidator emailValidator = EmailValidator.getInstance();
        System.out.println(emailValidator.isValid("sdfs@asdf"));
        System.out.println(emailValidator.isValid("sdfs@asdf.com"));
        System.out.println(emailValidator.isValid("sdfsasdf.com"));
        System.out.println(emailValidator.isValid("sdfs@as.ua"));

        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString());

        System.out.println(RandomStringUtils.random(10, true, true));
    }
}
