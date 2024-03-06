##### 使用OpenSSL生成 CA 私钥和公钥文件

```
openssl genrsa -aes256 -out ca-key.pem 4096
```

这里需要输入一个密码，后面要用到

```
openssl req -new -x509 -days 365 -key ca-key.pem -sha256 -out ca.pem
```

这里需要输入密码、国家、省、市、组织、单位、Common name、邮箱等

Common name需要填docker daemon部署的主机ip

```
 openssl genrsa -out server-key.pem 4096
```

```
openssl req -subj "/CN=$HOST" -sha256 -new -key server-key.pem -out server.csr
```

将/CN=后面的$HOST替换成docker所在主机的IP地址。

```
echo subjectAltName = DNS:$HOST,IP:$HOST >> extfile.cnf
```

##### 使用 CA 签署公钥

同样要替换$HOST为docker所在主机IP地址。

```
echo extendedKeyUsage = serverAuth >> extfile.cnf
```

##### 生成签名证书

```
openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem -CAkey ca-key.pem \
  -CAcreateserial -out server-cert.pem -extfile extfile.cnf
```

##### 创建客户端密钥和证书签名请求

```
 openssl genrsa -out key.pem 4096
 openssl req -subj '/CN=client' -new -key key.pem -out client.csr
```

```
echo extendedKeyUsage = clientAuth > extfile-client.cnf
```

```
 rm -v client.csr server.csr extfile.cnf extfile-client.cnf
```

```
 chmod -v 0400 ca-key.pem key.pem server-key.pem
```

```
 chmod -v 0444 ca.pem server-cert.pem cert.pem
```

##### 修改daemon.json配置

```
vim /etc/docker/daemon.json
```



```
{
        "hosts": [
                   "fd://",
                   "unix:///var/run/docker.sock",
                   "tcp://0.0.0.0:2375"
        ],
        "tls": true,
        "tlsverify": true,
        "tlscacert": "/etc/docker/tls/ca.pem",
        "tlscert": "/etc/docker/tls/server-cert.pem",
        "tlskey": "/etc/docker/tls/server-key.pem"
}

```

去除`/lib/systemd/system/docker.service`文件中`ExecStart`里全部的`-H`选项

##### 重启docker

```
systemctl daemon-reload
systemctl docker restart
```

