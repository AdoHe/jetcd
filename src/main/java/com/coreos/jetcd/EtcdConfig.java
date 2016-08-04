package com.coreos.jetcd;

/**
 *
 * EtcdConfig is responsible for extracting data from a config file.
 */

import com.esotericsoftware.yamlbeans.YamlReader;
import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.SslContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.List;


public class EtcdConfig {

    // endPoints is a list of URLs
    private List<String> endPoints;

    // dialTimeout is the timeout for failing to establish a connection.
    private long dialTimeout;

    //certfile is the filename of the certificate file.
    private String certfile;

    //keyfile is the filename of the key file.
    private String keyfile;

    //cAfile is the filename fo the CA file.
    private String cAfile;

    // sslContext holds the client secure credentials, if any.
    private SslContext sslContext;

    // username is a username for authentication
    private String username;

    // password is a password for authentication
    private String password;

    public List<String> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(List<String> endPoints) {
        this.endPoints = endPoints;
    }

    public long getDialTimeout() {
        return dialTimeout;
    }

    public void setDialTimeout(long dialTimeout) {
        this.dialTimeout = dialTimeout;
    }

    public SslContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    public String getCertfile() {
        return certfile;
    }

    public void setCertfile(String certfile) {
        this.certfile = certfile;
    }

    public String getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(String keyfile) {
        this.keyfile = keyfile;
    }

    public String getcAfile() {
        return cAfile;
    }

    public void setcAfile(String cAfile) {
        this.cAfile = cAfile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param fpath
     * @return an EtcdConfig object.
     * @throws IOException
     * @throws CertificateException
     */

    public  EtcdConfig configFromFile(String fpath) throws IOException, CertificateException {
        YamlReader yamlReader = new YamlReader(new FileReader(fpath));
        Map yamlMap = (Map)yamlReader.read();
        EtcdConfig cfg = new EtcdConfig();
        cfg.setEndPoints((List<String>) yamlMap.get("endPoints"));
        cfg.setDialTimeout((Long.parseLong((String)yamlMap.get("dialTimeout"))));
        cfg.setCertfile((String) yamlMap.get("certfile"));
        cfg.setKeyfile((String) yamlMap.get("keyfile"));
        cfg.setcAfile((String) yamlMap.get("cAfile"));
        cfg.setUsername((String)yamlMap.get("username"));
        cfg.setPassword((String)yamlMap.get("password"));
        if((boolean)yamlMap.get("insecureTransport") == true){
            cfg.setSslContext(null);
            return cfg;
        }
        else{
            SslContext cert = null;
            if(this.certfile!= "" && this.keyfile!= "") {
                if (this.cAfile != "") {
                    cert = GrpcSslContexts.forClient().keyManager(new File(this.certfile), new File(this.keyfile)).trustManager(new File(this.cAfile)).build();
                }
                else {
                    cert = GrpcSslContexts.forClient().keyManager(new File(this.certfile),new File(this.keyfile)).build();
                }
                cfg.setSslContext(cert);
            }
            return cfg;
        }
    }
}