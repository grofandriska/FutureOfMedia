package hu.futureofmedia.task.contactsapi.config;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneNumberConfiguration {

    @Bean
    public PhoneNumberUtil getPhoneNumberBean() {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        return util;
    }
}
