1.这个工具只支持sSH方式，Telnet方式还不支持，在连接方式上带有一定的局限性
运行前准备:
1、首先确定服务器是否开通了SSH端口22，可以通过netstat -ano | grep 22 或者 netstat -ano | grep *.22 确认。还可以通过sshd_config文件确认。
开通SSH权限
修改服务器上的sshd config文件(/etc/33h/),PasswordAuthentication no改为ves。
重新启动ssh进程:/etc/init.d/ashd restart

注意:我们连接到的准生产环境，基本上都是SSH方式22端口:我们自己的性能测试环境开通的是Telnet 23端口，需要我们手动开通SSH

2、在服务器执行 uname -a，确认服务器操作系统类型，<osType>用到
3、确定要上传的nmon工具以及工具所在路径，<NmonHome>和<NmonFileName>用到
4、确定nmon监控结果放置路径，<resultHome>用到
5、确定被监控服务器上，nmon工具以及nmon监控结果放置路径，<NmonRoot>用到
例如:
run setup :安装nmon文件 
run start :运行nmon文件 
run stop :停止nmon
run download :下载nmon结果 
run Uninstall :卸载nmon