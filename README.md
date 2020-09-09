# 698报文

该项目是工作之余的学习之作。

本意是希望能将电表相关报文的组建以及解析重新进行封装，后期由于工作任务较重，故仅实现了对698报文的组装部分，若需要解析可以参看



[报文解析]: https://github.com/qinling0918/FrameMessageParser




#### 示例：

解析

```java
     // 698报文
        String frame = "684200c30501000000000000f495870100 f1000b00 00 01 02 04 09 06 000000000001 09085101000000131f6857086f9bc745999f041357084ef5715de58dd5d2 0000 60f1 16".replaceAll(" ", "");
        // 解析698报文，解析粒度到数据域，需要解析数据域请参考 FrameMessageParser
       new Frame698.Parser(frame).toFormatString();
        // 698报文帧: 684200c30501000000000000f495870100f1000b0000010204090600000000000109085101000000131f6857086f9bc745999f041357084ef5715de58dd5d2000060f116
        // 68 帧头
        // 4200 帧长度L:66字节（10进制）
        // c3 控制码C 服务器对客户机请求的响应  不分帧 不扰码  用户数据(应用连接管理及数据交换服务)
        // 05 服务器地址SA第一个字节
        // 010000000000 服务器地址SA: 000000000001  地址类型：单地址
        // 00 客户端地址CA: 0
        // f495 帧头校验HCS
        // 870100f1000b0000010204090600000000000109085101000000131f6857086f9bc745999f041357084ef5715de58dd5d20000 链路用户数据
        // 60f1 帧校验
        // 16 帧尾

     
```



#### 组建数据域

```java
   // 组建任务数据，正常读取一条数据
       ClientAPDU.getRequestNormal("40010200").toHexString();
        // 0501004001020000
        
        // 组建任务数据，正常读取多条数据
        ClientAPDU.getRequestNormalList(2, "20000200", "20010200").toHexString();
        // 05020202200002002001020000
        
        // 组建任务数据，读取一条日冻结数据数据 （）
        ClientAPDU.getRequestRecord(3, "50040200",
                RSD.select1("20210200", new DateTimeS(2016, 1, 20)),
                RCSD.newInstance().addOAD("20210200", "00100200")
        ).toHexString();
        // 0503035004020001202102001C07E00114000000020020210200000010020000

 		// 组建任务数据，读取一条日冻结数据数据 （）
        ClientAPDU.getRequestRecord(
        				4, 
        				"60120300",
        				RSD.select5(new DateTimeS(2016, 1, 20),
                        MS.userAddress("040000000121", "040000000122", "040000000123", "040000000124", "040000000125")),
                        RCSD.newInstance().addOAD("40010200", "60400200", "60410200", "60420200").addROAD("50040200", "00100200", "00200200"))
                        .toHexString();
        //050304601203000507E0011400000003051800000100000000000000000000000000000000010010000118000001000000000000000000000000000000000100100010180000010000000000000000000000000000000001001000111800000100000000000000000000000000000000010010010018000001000000000000000000000000000000000100100101050040010200006040020000604102000060420200015004020002001002000020020000
        // 设置当前时间
        ClientAPDU.setRequestNormal(2, "40000200", new DateTimeS(2016, 1, 20, 16, 27, 11)).toHexString(); // 060102400002001C07E00114101B0B00
        // 
        ClientAPDU.setRequestNormalList(3, new SetNormal("40010200", new OctetString("000000000001"))).toHexString(); // 0602030140010200090600000000000100
        
       ClientAPDU.setRequestNormalList(new SetRequestNormalList.Builder()
                        .setPiid(3)
                        .addNormal(new SetNormal("40010200", new OctetString("000000000001")))
                        .addNormal(new SetNormal("40000200", new DateTimeS(2016, 1, 20, 16, 27, 11))).build()).toHexString();
        // 06020302400102000906000000000001400002001C07E00114101B0B00
```

组建完整报文

```java
// 数据域为字符串    
Frame698 frame698ForString = new Frame698.Builder()
         .setServerAddress("123456789012",ADDRESS_TYPE_SINGLE)
         .setLinkDataStr("850101400502010109061111111111110000")
         .build();
// 6821004305129078563412001B48850101400502010109061111111111110000B1D616

// 数据域为Apdu对象
Frame698 frame698ForApdu = new Frame698.Builder()
         .setServerAddress("123456789012",ADDRESS_TYPE_SINGLE)
         .setLinkData(ClientAPDU.getRequestNormal("40010200"))
         .build();
// 681700430509214365870900ACC50501004001020000ED0316

// 复制已有的Frame698，然后重新赋值 
Frame698 frame698FromOther = new Frame698.Builder(frame698ForApdu)
         .setLinkDataStr("850101400502010109061111111111110000")
         .build();
// 68210043050921436587090014A3850101400502010109061111111111110000B1D616

```

