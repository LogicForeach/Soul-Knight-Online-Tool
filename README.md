# 环境准备

+ 腾讯云/阿里云服务器一台，如果能搞到学生机的话是很便宜的
+ 两台电脑，台式笔记本都行
+ 电脑和服务器需要配置JAVA环境

## 服务器上的操作

将server.jar传到服务器上，并运行

```
java -jar server.jar
```

## 电脑上的操作

1. 手机开热点
2. 电脑连接热点
3. 用 `java -jar client.jar` 运行client.jar
4. 输入服务器的外网IP地址
5. 如果要创建房间就输入1，如果要加入房间就输入2

## 实现联机

之前输入1的去创建一个房间，然后输入2的就可以在加入游戏中看到房间了，点击进入即可。只有输入1的创建房间能实现转发，如果输入1的想加入输入2的房间，需要关闭电脑上的jar程序，重新进入，并输入2，让对方也重启程序输入1。

## 相关内容

实现的代码都在github上了：

https://github.com/LogicForeach/Soul-Knight-Online-Tool

实现过程我用CSDN做了一个详记，其中有对实现原理和实现方法的讨论：

[计算机网络实践之元气骑士公网异地联机(一) 初探局域网联机过程](https://blog.csdn.net/lzs781/article/details/96515338)

[计算机网络实践之元气骑士公网异地联机(二) 两种方案可行性分析](https://blog.csdn.net/lzs781/article/details/97617650)

[计算机网络实践之元气骑士公网异地联机(三) 完善转发机的转发规则](https://blog.csdn.net/lzs781/article/details/97617723)

