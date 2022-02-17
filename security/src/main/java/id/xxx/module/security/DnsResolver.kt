package id.xxx.module.security

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

class DnsResolver(okHttpClient: OkHttpClient) : Dns {

    private val doh by lazy {
        val hosts = listOf(
            InetAddress.getByName("162.159.36.1"),
            InetAddress.getByName("162.159.46.1"),
            InetAddress.getByName("1.1.1.1"),
            InetAddress.getByName("1.0.0.1"),
            InetAddress.getByName("162.159.132.53"),
            InetAddress.getByName("2606:4700:4700::1111"),
            InetAddress.getByName("2606:4700:4700::1001"),
            InetAddress.getByName("2606:4700:4700::0064"),
            InetAddress.getByName("2606:4700:4700::6400")
        )

        DnsOverHttps.Builder().client(okHttpClient)
            .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
            .bootstrapDnsHosts(hosts)
            .resolvePrivateAddresses(true)
            .build()
    }

    override fun lookup(hostname: String): List<InetAddress> {
        return doh.lookup(hostname)
    }
}