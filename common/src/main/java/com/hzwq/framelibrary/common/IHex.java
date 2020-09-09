package com.hzwq.framelibrary.common;


import com.hzwq.framelibrary.common.throwable.ParseFrameException;
import com.hzwq.framelibrary.common.util.NumberConvert;

import java.util.Locale;

/**
 * Created by qinling on 2019/4/29 10:36
 * Description:
 */
public interface IHex {
    default byte[] getBytes() {
        // return toHexString().getBytes();
        return NumberConvert.hexStringToBytes(toHexString());
    }

    String toHexString();

    /**
     * 用来拼接 IHex对象。
     *
     * @param hexes
     * @return
     */
    default String concat(IHex... hexes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (IHex hex : hexes) {
            stringBuilder.append(hex.toHexString());
        }
        return stringBuilder.toString().toLowerCase(Locale.CHINA);
    }

    /**
     * 用来分割String 到 IHEX
     *
     * @param source   IHex 格式的数据,能够获取 byte[]
     * @param byteSize 每个数据的字节数
     * @return 将完整的16进制字符串 按照字节分割成多条数据.
     */
    default String[] split(IHex source, int... byteSize) {
        int byteSizeLen = byteSize.length;
        String[] hexs = new String[byteSizeLen];
        byte[] bytes = source.getBytes();
        int totalByteSize = 0;
        for (int size : byteSize) {
            totalByteSize += Math.abs(size);
        }
        if (totalByteSize > bytes.length) {
            throw new ParseFrameException(
                    new IndexOutOfBoundsException("总字节数大于数据源字节数"));
        }

        int off = 0;
        for (int i = 0; i < byteSizeLen; i++) {

            boolean isReverse = byteSize[i] < 0;
            int length = Math.abs(byteSize[i]);

            if (byteSize[i] == 0) {
                byte[] b = new byte[2];
                System.arraycopy(bytes, off, b, 0, 2);
                length = Integer.parseInt(NumberConvert.bytesToHexString(b), 16);
                System.out.print("length:  " + length);
                // 数据源字节数无法满足解析.
                if (bytes.length - off < 0) {
                    throw new ParseFrameException(
                            new IndexOutOfBoundsException("总字节数大于数据源字节数"));
                }
                off += length;
                System.out.print("off:  " + off);

            }


            byte[] b = new byte[length];
            System.arraycopy(bytes, off, b, 0, length);
            off += length;
            b = isReverse ? NumberConvert.bytesReverse(b) : b;
            hexs[i] = NumberConvert.bytesToHexString(b);
        }

        return hexs;
    }
    /**
     * 根据传入的字节数,将一个完整的字符串按照字节分割.
     *
     * @param zeroBytes 非0的值, 该值表示在分割时,若某项字节数传入的是0
     *                  则将按照该值获取紧跟着的 zeroBytes的绝对值 个字节,这zeroBytes 个字节就是紧接着的数据项的长度
     *                  若zeroBytes 为 负值,则表示紧接着的这zeroBytes 个字节长度域需要高低字节翻转.
     * @param source    16进制字符串
     * @param byteSize  每个数据项的字节数. 若需要按字节翻转,则传入 负值.
     * @return 按照传入的字节数对字符串进行分割
     * <p>
     * 若是传入的数为 负值,则表示对应的字符串将会进行翻转
     * eg: split("110001445566778899",1,1,2,-3)   ---> ["11","00","0144",776655"]
     * eg: split("110001445566778899",1,1,-2,-3)   ---> ["11","00","4401",776655"]
     * // 若是 0,则按照紧接着的 zeroBytes 个字节表示的数值 作为长度.然后根据该数值对字符串进行截取,获取数据.并将返回 长度+数据.
     * <p>
     * eg: split(2,"110001445566778899",1,0,2,-3)   ---> ["11","000144","6655",998877"]
     * eg: split(1,"110001445566778899",1,0,-2,-3)   ---> ["11","00",4401","776655"]
     * eg: split(1,"110001445566778899",1,1,0,-2,-3)   ---> ["11","00",0144","6655",998877"]
     * <p>
     * <p>
     * <p>
     * 以安全单元报文为例
     * <p>
     * 二次发行安全单元
     * <p>
     * 数据名称	      字节数  数据格式
     * ESAM类型      	1	   Hex
     * 发行数据内容      2+N     HEX  (其中 2 表示 N 的字节值,需要从报文数据中截取,若是这
     * 两个字节的数据,需要高低字节翻转,则传 -2 即可.)
     * 若是使用该方法,则传值为split(2,二次发行数据域16 进制字符串,1,0)
     */

    default String[] split(int zeroBytes, String source, int... byteSize) {
        if (zeroBytes == 0) {
            throw new IndexOutOfBoundsException("zeroBytes不能为 0");
        }
        // 若是表示不固定字节长度的数据的长度域为负值时,则需要将字节翻转.
        boolean isLenReverse = zeroBytes < 0;
        zeroBytes = Math.abs(zeroBytes);
        int byteSizeLen = byteSize.length;
        String[] hexs = new String[byteSizeLen];
        byte[] bytes = NumberConvert.hexStringToBytes(source);
        if (null == bytes) return null;
        int totalByteSize = 0;
        for (int size : byteSize) {
            totalByteSize += Math.abs(size);
        }
        if (totalByteSize > bytes.length) {
            throw new IndexOutOfBoundsException("总字节数大于数据源字节数");
        }

        int off = 0;
        for (int i = 0; i < byteSizeLen; i++) {

            boolean isReverse = byteSize[i] < 0;
            int length = Math.abs(byteSize[i]);

            if (byteSize[i] == 0) {
                byte[] b = new byte[zeroBytes];
                System.arraycopy(bytes, off, b, 0, zeroBytes);
                // 是否需要翻转
                b = isLenReverse ? NumberConvert.bytesReverse(b) : b;
                String lenHex = NumberConvert.bytesToHexString(b);

                length = Integer.parseInt(lenHex, 16);
                System.out.println("length:  " + length);
                // 数据源字节数无法满足解析.
                if (bytes.length - off < 0) {
                    throw new IndexOutOfBoundsException("总字节数大于数据源字节数");
                }
                length += zeroBytes;
                // off += (length+2) ;
                // System.out.println("off:  "+off);

            }


            byte[] b = new byte[length];
            System.arraycopy(bytes, off, b, 0, length);
            off += length;
            b = isReverse ? NumberConvert.bytesReverse(b) : b;
            hexs[i] = NumberConvert.bytesToHexString(b);
        }

        return hexs;
    }

    /**
     * 根据传入的字节数,将一个完整的字符串按照字节分割.
     *
     * @param source   16进制字符串
     * @param byteSize 每个数据项的字节数. 若需要按字节翻转,则传入 负值.
     * @return 按照传入的字节数对字符串进行分割
     * <p>
     * 若是传入的数为 负值,则表示对应的字符串将会进行翻转
     * eg: split("110001445566778899",1,1,2,-3)   ---> ["11","00","0144",776655"]
     * eg: split("110001445566778899",1,1,-2,-3)   ---> ["11","00","4401",776655"]
     * // 若是 0,则按照紧接着的两个字节作为长度.并将返回 长度+数据.
     * eg: split("110001445566778899",1,0,2,-3)   ---> ["11","000144","6655",998877"]
     * <p>
     * eg: split(2,"110001445566778899",1,0,2,-3)   ---> ["11","000144","6655",998877"]
     * eg: split(1,"110001445566778899",1,0,-2,-3)   ---> ["11","00",4401","776655"]
     * eg: split(1,"110001445566778899",1,1,0,-2,-3)   ---> ["11","00",0144","6655",998877"]
     */
    default String[] split(String source, int... byteSize) {
        return split(1, source, byteSize);
    }





}
