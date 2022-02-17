package id.xxx.module.security

import java.security.KeyStore
import java.security.cert.Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SSLCertificateConfigurator {

    @JvmStatic
    fun getSSLContext(certificate: Certificate): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManager(certificate).trustManagers, null)
        return sslContext
    }

    @JvmStatic
    fun getX509TrustManager(certificate: Certificate?): X509TrustManager {
        val trustManagers = getTrustManager(certificate).trustManagers
        return if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) throw IllegalStateException(
            "Unexpected default trust managers: ${Arrays.toString(trustManagers)}"
        ) else trustManagers[0] as X509TrustManager
    }

    @JvmStatic
    private fun getTrustManager(certificate: Certificate?): TrustManagerFactory {
        val trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm)
        trustManagerFactory.init(getKeystore(certificate))
        return trustManagerFactory
    }

    @JvmStatic
    private fun getKeystore(certificate: Certificate?): KeyStore {
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)
        return keyStore
    }
}