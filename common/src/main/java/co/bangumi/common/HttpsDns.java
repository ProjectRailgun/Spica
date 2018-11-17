package co.bangumi.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

import static java.net.InetAddress.getAllByName;

public class HttpsDns implements Dns {

    private HttpsDnsService dnsService;

    public HttpsDns() {
        dnsService = new HttpsDnsService();
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (dnsService == null)
            return Dns.SYSTEM.lookup(hostname);

        try {
            List<String> ips = dnsService.query(hostname);
            if (ips == null || ips.size() == 0) {
                return Dns.SYSTEM.lookup(hostname);
            }

            List<InetAddress> result = new ArrayList<>();
            for (String ip : ips) {
                result.addAll(Arrays.asList(getAllByName(ip)));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Dns.SYSTEM.lookup(hostname);
    }
}
