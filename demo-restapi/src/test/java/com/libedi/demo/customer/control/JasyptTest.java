package com.libedi.demo.customer.control;

import java.util.Set;
import java.util.TreeSet;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.registry.AlgorithmRegistry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Available Jasypt Algorithm test
 * @author Sang jun, Park
 *
 */
public class JasyptTest {

	@Test
    public void test() {
        Set<String> supported = new TreeSet<>();
        Set<String> unsupported = new TreeSet<>();
        for (Object oAlgorithm : AlgorithmRegistry.getAllPBEAlgorithms()) {
            String algorithm = (String) oAlgorithm;
            try {
                SimpleStringPBEConfig pbeConfig = new SimpleStringPBEConfig();
                pbeConfig.setAlgorithm(algorithm);
                pbeConfig.setPassword("changeme");
                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                encryptor.setConfig(pbeConfig);

                String encrypted = encryptor.encrypt("foo");
                String decrypted = encryptor.decrypt(encrypted);
                Assert.assertEquals("foo", decrypted);
                supported.add(algorithm);
            } catch (EncryptionOperationNotPossibleException e) {
                unsupported.add(algorithm);
            }
        }
        System.out.println("Supported");
        supported.forEach(alg -> System.out.println("   " + alg)); 
        System.out.println("Unsupported");
        unsupported.forEach(alg -> System.out.println("   " + alg)); 
    }
}
