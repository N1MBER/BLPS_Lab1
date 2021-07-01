package com.blps_lab1.demo.main_server.configuration;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.blps_lab1.demo.main_server.XA.PaymentAddXA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

@Configuration
@EnableTransactionManagement
public class AtomikosConfiguration {

    @Bean
    public PaymentAddXA paymentAddXA(){
        return new PaymentAddXA();
    }

    @Bean(name = "transactionManager")
    public JtaTransactionManager jtaTransactionManager() throws SystemException {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionManager());
        jtaTransactionManager.setRollbackOnCommitFailure(true);
        return jtaTransactionManager;
    }

    @Bean(destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setTransactionTimeout(300);
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }
}
