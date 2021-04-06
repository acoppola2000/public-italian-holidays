package it.acoppola2000.publicItalianHolidays.cache;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

class CustomSSLFactoryBuilder {

    static SSLSocketFactory buildSSLSocketFactory(String clientKeyStorePath, String clientKeyStorePass,
                                                  String trustStorePath, String trustStoreStorePass) {

        try {
            InputStream keyStoreStream = new FileInputStream(clientKeyStorePath);
            KeyStore  ks = KeyStore.getInstance("JKS");
            ks.load(keyStoreStream, clientKeyStorePass.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); // DefaultAlgorithm is PKIX
            kmf.init(ks,clientKeyStorePass.toCharArray());

            InputStream trustStoreStream = new FileInputStream(trustStorePath);
            KeyStore ksTrust = KeyStore.getInstance("JKS");
            ksTrust.load(trustStoreStream, trustStoreStorePass.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); // DefaultAlgorithm is PKIX
            tmf.init(ksTrust);

            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            SSLSocketFactory sslSF = sslCtx.getSocketFactory();
            return sslSF;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;

    }

}
