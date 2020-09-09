package com.hzwq.framelibrary.protocol698;

import android.support.annotation.Size;
import android.support.annotation.VisibleForTesting;
import android.util.SparseArray;

import com.hzwq.framelibrary.annotation.Attribute;
import com.hzwq.framelibrary.common.TestA;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.apdu.APDU;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestRecord;
import com.hzwq.framelibrary.protocol698.apdu.client.set.SetRequestNormalList;
import com.hzwq.framelibrary.protocol698.data.Date;
import com.hzwq.framelibrary.protocol698.data.DateTime;
import com.hzwq.framelibrary.protocol698.data.DateTimeS;
import com.hzwq.framelibrary.protocol698.data.MS;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.ScalerUnit;
import com.hzwq.framelibrary.protocol698.data.TI;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetNormal;
import com.hzwq.framelibrary.protocol698.data.enums.TimeUnit;
import com.hzwq.framelibrary.protocol698.data.manager.OIManager;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;


/**
 * Created by qinling on 2019/4/18 15:17
 * Description:
 */
public class Test {
    protected static String getFormatValueWithUnit(long value, String unit, int pow) {
        double num =  (value*(Math.pow(10,pow)));
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(pow); i++) {
            foramt.append(0);
        }
        foramt.append(unit);
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(num);
    }

    public static String getReportStr(String oad,int status) {
        return "通道： " + oad + "\n" +
                "上报状态: \n" + getReportStr(status) + "\n";
    }
    public static String getReportStr(int status) {
        String[] reportArr = new String[]{
                "事件发生上报标识", "事件发生上报确认标识",
                "事件结束（恢复）上报标识", "事件结束（恢复）上报确认标识"
        };
        String binaryStr  = NumberConvert.toBinaryStr(status,4);
        System.out.println(binaryStr);
        char[] chars = binaryStr.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < reportArr.length; i++) {
            String result = reportArr[i].substring(reportArr[i].length()-4,reportArr[i].length()-2);
            stringBuilder.append(" ").append(i+1).append(".").append(reportArr[i]).append(": ").append(chars[3-i]=='0'?"未":"已").append(result).append("\n");
        }

        return stringBuilder.toString();
    }
    public static void main(String[] args) {

       /* System.out.println(ClientAPDU.getRequestRecord(3, "50040200",
                RSD.select2(new OAD("50040200"),new DateTimeS(),new DateTimeS(),new TI(TimeUnit.SECOND,new LongUnsigned(1))),
               // RSD.select9(1),
              //  RCSD.newInstance().addOAD("20210200", "202C0200")
                RCSD.newInstance().addOAD("20210200", "202C0200")
        ).toHexString());*/
        System.out.println("直接读取: "+ClientAPDU.getRequestNormal( "30000D00" ).toHexString());
        System.out.println("事件类记录型数据，无条件 读属性值13  :"+ClientAPDU.getRequestRecord(3, "30000D00",
                RSD.selectNull(),
                // RSD.select9(1),
                //  RCSD.newInstance().addOAD("20210200", "202C0200")
                RCSD.newInstance()
        ).toHexString());
        System.out.println("直接读取: "+ClientAPDU.getRequestNormal( "30000300" ).toHexString());
        System.out.println("直接读取: "+ClientAPDU.getRequestRecord(0,"50040200",RSD.select1("20210200",new DateTimeS()),RCSD.newInstance().addOAD("01000200")).toHexString());
        System.out.println("事件类记录型数据，无条件 读属性值3  :"+ClientAPDU.getRequestRecord(3, "30000300",
                RSD.selectNull(),
                // RSD.select9(1),
                //  RCSD.newInstance().addOAD("20210200", "202C0200")
                RCSD.newInstance()
        ).toHexString());
        // test12345();
        // test12345();
       // System.out.println(OIManager.getInstance().getObjName("1010"));
       // System.out.println(OIManager.OI.ENERGY.getOIs().size());
      //  System.out.println(OIManager.OI.MAXIMUM_DEMAND.getOIs().size());
       // testOiManager();
      //  getNum();
        // test3();
//        System.out.println(getReportStr("12345678",2));

     //   System.out.println( new Frame698.Builder().setLinkDataStr("85030300100200010105060000000006000000000600000000060000000006000000000000").build().toHexString());
     /*   RecoverableString recoverableString = new RecoverableString("301B0400011200000000");
        System.out.println(recoverableString.subCurrentSting(0,8));
        System.out.println(recoverableString.getHistoryString());
        System.out.println(recoverableString.getCurrentSting());
        System.out.println(recoverableString.subCurrentSting(0,2));
        System.out.println(recoverableString.getHistoryString());
        System.out.println(recoverableString.getCurrentSting());
        System.out.println(recoverableString.subCurrentSting(0,2));
        System.out.println(recoverableString.getHistoryString());
        System.out.println(recoverableString.getCurrentSting());
        System.out.println(recoverableString.getHistoryStringLength());
        System.out.println(recoverableString.getOriginalString());*/

       // String oadS = "12345678";
       // getFormatValueWithUnit(oadS,new String[]{"11111111,22222222,33333333"},3);
       // System.out.println(oadS);

       // test2();
        //   System.out.println((A) new B());
        // System.out.println();
    //    getNum();"
    }

    private static void testOiManager() {
        int totolSize = 0;
        for (int i = 1; i <= 26; i++) {
            HashMap<String, OIManager.Inner> innerHashMap = OIManager.getInstance().getOIsByInfterface(i);
            int size = innerHashMap.size();
            if (size == 1){
                System.out.println(i+" : " +size+"   -- "+ innerHashMap.keySet().iterator().next());
            }
            if (i == 8){
                System.out.println("[ \n" );
                for (String s: innerHashMap.keySet()) {
                    System.out.println(i+" : " +s);
                }
                System.out.println("] \n" );
            }
                else{
                System.out.println(i+" : " +size);
            }

            totolSize +=size;
        }


        System.out.println("totolSize: " + totolSize);
        System.out.println("totolSize1: " + OIManager.getInstance().getOIs().size());
    }

    private static void test3() {

     //   System.out.println( new Frame698.Parser("fefefefe68c600e3050100000000000083ae0000900082024485020202401402000101040203110811011101020311081101110102031108110111010203110811011101401602000101080108020311001101110102031106110111020203110d11281103020311111101110402031111110111040203111111011104020311111101110402031111110111040108020311001101110102031106110111020203110d1128110302031111110111040203111111011104020311111101110402031111110111040203ac7816").toFormatString());
        System.out.println( new Frame698.Builder().setLinkDataStr("85010110100200010103020206000000001c07e3051c0b0f10020206000000001c07e3061c0b0f10020206000000001c07e3071c0b0f100000").build().toHexString());
        System.out.println( new Frame698.Builder().setLinkDataStr("850101400502000101030906111111111111090522222222220904333333330000").build().toHexString());
        System.out.println( new Frame698.Builder().setLinkDataStr("850101400502010109061111111111110000").build().toHexString());
        byte[] bytes =NumberConvert. hexStringToBytes("FE");

        //this.pow = toUnsignedInt(bytes[0]);


        System.out.println(bytes[0]+ ":  "+ Integer.toHexString((byte)-2));
        System.out.println(new ScalerUnit("fe21").getPow());
        System.out.println(new ScalerUnit("fe21").getUnitStr());

        System.out.println(OIManager.getInstance().getInterfClass("F205"));
        System.out.println(OIManager.getInstance().getObjName("F205"));
        // test5();
    }

    private static void test5() {
        String bits =  NumberConvert.hexStrToBinaryStr("114F");
        char[] bitChar = bits.toCharArray();
        for (int i = 0; i < bitChar.length; i++) {
            System.out.println(i+":  "+ (bitChar[i]=='1'));
        }
        System.out.println(bits.subSequence(9,11).toString());

        String frame = "114F";
        byte[] bytes = NumberConvert.hexStringToBytes("114F");
        BitSet bitSet = BitSet.valueOf(bytes);
        // BitSet bitSet = BitSet.valueOf((byte[])new byte[]{0x04});
        //BitSet bitSet = new BitSet(1);
        // bitSet.set(0);
        for (int i = 0; i < 16; i++) {
            System.out.println(i+":  "+bitSet.get(i));
        }
        System.out.println(NumberConvert.bytesToBinaryString(bitSet.get(9,11).toByteArray()));
        System.out.println(NumberConvert.bytesToHexString(bitSet.get(9,11).toByteArray()));
    }

    private static void test2() {
        String frame = "684200c30501000000000000f495870100 f1000b00 00 01 02 04 09 06 000000000001 09085101000000131f6857086f9bc745999f041357084ef5715de58dd5d2 0000 60f1 16".replaceAll(" ","");
        System.out.println(new Frame698.Parser(frame).toFormatString());
        System.out.println(ClientAPDU.getRequestNormal("40180200").toHexString());
        String client =  "0503035004020002202102001C07E305030000001C07E3050500000000020020210200000020020000";
        System.out.println(String.format("1000%02x%s0108%s",client.length()/2,client,"11111111"));

        //  test();
        System.out.println(ClientAPDU.getRequestNormal("40010200").toHexString());
        System.out.println(ClientAPDU.getRequestNormalList(2, "20000200", "20010200").toHexString());
        System.out.println(ClientAPDU.getRequestRecord(3, "50040200",
                RSD.select1("20210200", new DateTimeS(2016, 1, 20)),
                RCSD.newInstance().addOAD("20210200", "00100200")
        ).toHexString());

        System.out.println(ClientAPDU.getRequestRecord(4, "60120300",
                RSD.select5(new DateTimeS(2016, 1, 20),
                        MS.userAddress("040000000121", "040000000122", "040000000123", "040000000124", "040000000125"))
                , RCSD.newInstance().addOAD("40010200", "60400200", "60410200", "60420200").addROAD("50040200", "00100200", "00200200")
        ).toHexString());
        //  System.out.println(ClientAPDU.getRequestNxet(9, 1).toHexString());

        System.out.println(ClientAPDU.setRequestNormal(2, "40000200", new DateTimeS(2016, 1, 20, 16, 27, 11)).toHexString());
        System.out.println(ClientAPDU.setRequestNormalList(3, new SetNormal("40010200", new OctetString("000000000001"))).toHexString());
        System.out.println(ClientAPDU.setRequestNormalList(
                new SetRequestNormalList.Builder()
                        .setPiid(3)
                        .addNormal(new SetNormal("40010200", new OctetString("000000000001")))
                        .addNormal(new SetNormal("40000200",new DateTimeS(2016, 1, 20, 16, 27, 11))).build()).toHexString());

        // System.out.println(ClientAPDU.getRequestNxet(9, 1).toHexString());
        //String []oads =  new Date().split(new Dat11eTimeS(2016,1,0),1,1,0);
        String []oads =  new Date().split("110001445566778899",1,1,0,-2,-3);
        for (String oad: oads ) {
            System.out.println(oad);
        }
    }

    private static void getNum() {
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = B.class;
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field f : fieldList) {
            System.out.println("getFields---" +f.getName());
        }
       // Field[] fields = B.class.getDeclaredFields();
        Field[] fields = fieldList.toArray(new Field[fieldList.size()]);
     //   Field[] fields = B.class.getDeclaredFields();
      //  System.out.println(fields.length);

        for (int i = 0; i < fields.length; i++) {
            Attribute testA =  fields[i].getAnnotation(Attribute.class);
            if (null != testA){
                System.out.println("index000: "+i+"  "+testA.name()+"  "+ testA.value());
            }
            //System.out.println("index111: "+i+"  "+fields[i].getInt(new A()));
            //   System.out.println("index111: "+i+"  "+fields[i].getAnnotation(TestA.class).name()+"  "+ fields[i].getAnnotation(TestA.class).gid());
            //  System.out.println("index111: "+i+"  "+testA.name()+"  "+ testA.gid());
        }

        for (int i = 0; i < fields.length; i++) {
            TestA testA =  fields[i].getAnnotation(TestA.class);
            if (null != testA){
                System.out.println("index111: "+i+"  "+testA.name()+"  "+ testA.gid());
            }
            //System.out.println("index111: "+i+"  "+fields[i].getInt(new A()));
         //   System.out.println("index111: "+i+"  "+fields[i].getAnnotation(TestA.class).name()+"  "+ fields[i].getAnnotation(TestA.class).gid());
          //  System.out.println("index111: "+i+"  "+testA.name()+"  "+ testA.gid());
        }





        /* for (Annotation annotation : annotations) {

            SerializedName serializedName = (SerializedName) annotation;
            System.out.println(serializedName.value());
        }*/


    }

   // @TestA(name = "type", gid = Long.class) //类成员注解
    public static class A {

        public static final String A1 = "1234";
        public static final String A3 = "4321";
        // @Nullable

       public A( int b, int c) {
           this.b = b;
           this.c = c;
       }


        @Attribute(3)
      //  @SerializedName(4)
        int b = 2;
        //@Size()
      //  @SerializedName(3)
        int c = 2;
    }

    public static class B extends A {
        @Attribute(value =5,name = A3)
        int a;

        public B(int a ,int b, int c) {
            super(b, c);
        }
    }



    private static void test() {
        // 050303
        // 50040200
        // 01
        // 20210200
        // 1C07E00114000000
        // 02
        // 00
        // 20210200
        // 00
        // 00100200
        // 00

        //
        //
        // test698();
        //厂商代码（size(4)）+ 软件版本号（size(4)）+软件版本日期（size(6)）+硬件版本号（size(4)）+硬件版本日期（size(6)）+厂家扩展信息（size(8)）
        //  TOPS 0102 160731 0102 160731

        //   String visiableString = "54 4F 50 53 30 31 30 32 31 36 30 37 33 31 30 31 30 32 31 36 30 37 33 31 00 00 00 00 00 00 00 00";
        //  String visiableString = "54 4F 50 53 ";
        //   visiableString = visiableString.replaceAll(" ","");
        //  System.out.println( ClientAPDU.getRequest().getRequestRecord(new PIID(1),new OAD(""),new GetRecord(new OAD("20000200"),new RSD(5))).toHexString());
        //  System.out.println(NumberConvert.hexStrToAsciiString(visiableString));
        // System.out.println(  String.format(Locale.CHINA,"%x%x%x%x",1,44,3,4));
        //  System.out.println("0201" + new ROAD("50040200", "00100200", "00200200").toHexString());
        //  System.out.println(RCSD.newInstance().addROAD("50040200", "00100200", "00200200").toHexString());
       /* System.out.println(
                ClientAPDU.getRequest().
                        getRequestRecord(4,
                                "60120300",
                                RSD.select5(new DateTimeS(2016,1,20), MS.userAddress("040000000121","040000000122","040000000123","040000000124","040000000125")),
                             //   new RCSD(CSD.createOAD("40010200","60400200","60410200","60420200"),CSD.createROAD())
                                RCSD.newInstance().addOAD("40010200","60400200")
                               .addOAD("60410200","60420200")
                                        .addROAD("50040200","00100200","00200200")
        ).toHexString());

        System.out.println(
                ClientAPDU.getRequest().
                        getRequestRecord(3,
                                "50040200",
                                RSD.select1("20210200",new DateTimeS(2016,1,20)),
                                RCSD.newInstance().addOAD("20210200","00100200")
                               *//* RSD.select5(new DateTimeS(2016,1,20), MS.userAddress("040000000121","040000000122","040000000123","040000000124","040000000125")),
                                //   new RCSD(CSD.createOAD("40010200","60400200","60410200","60420200"),CSD.createROAD())
                                RCSD.newInstance()
                                        .addOAD("40010200")
                                        .addOAD("60400200")
                                        .addOAD("60410200")
                                        .addOAD("60420200")
                                        .addROAD("50040200","00100200","00200200")
                       *//* ).toHexString());
        System.out.println(
                ClientAPDU.getRequest().
                        getRequestNext(9,1
                                 ).toHexString());
        System.out.println(
                ClientAPDU.actionRequest().actionRequest(5,"00100100", new Integer(0))
                        .toHexString());

        System.out.println(
                ClientAPDU.actionRequest()
                        .actionThenGetRequestNormalList(new PIID(7),
                                SetThenGet
                                )
                        .toHexString());*/

        //  System.out.println((new ClientAPDU((new GetRequestNormal.Builder().setPiid(9).setOad("12345678").build())).setTimeOut(2,TimeUnit.DAY)).toHexString());
        //  System.out.println((new ClientAPDU((new GetRequestMD5().newBuilder().setPiid(9).setOad("12345678").build())).setTimeOut(2, TimeUnit.DAY)).toHexString());
        //  System.out.println((new ClientAPDU((new GetRequestMD5.Builder().setPiid(9).setOad("12345678").build()))).toHexString());
        //System.out.println(new LinkRequest.Builder().build().toHexString());
        //System.out.println(new LinkRequest().newBuilder().build().toHexString());
        //System.out.println(new LinkResponse.Builder().build().toHexString());
        System.out.println(ClientAPDU.getRequestNormal("40180200"));
      // String client =  "0503035004020002202102001C07E305030000001C07E3050500000000020020210200000020020000";
      //  System.out.println(String.format("1000%02x%s0108%s",client.length()/2,client,"11111111"));
        // System.out.println((new ClientAPDU((new GetRequestMD5(new ).setPiid(9).setOad("12345678").build()))).toHexString());
        // System.out.println(ClientAPDU.getRequestMD5( new GetRequestMD5().newBuilder().setPiid(9).setOad("12345678").build()).setTimeOut(0, TimeUnit.DAY).toHexString());
        //  System.out.println(ClientAPDU.getRequestNormal( new GetRequestNormal.Builder().setPiid(9).setOad("12345678").build()).setTimeOut(0, TimeUnit.DAY).toHexString());
        //   System.out.println(new ReportResponseRecordList.Builder().setOads("12345678").build().classify());
    }

    private static void test698() {
        Frame698.Parser frame698_0 = new Frame698().parse("683000010507091905162010000081008007E0051304080500008907E0051304080501025F07E005130408050202DA000016");
        Frame698.Parser frame698 = new Frame698().parse("fefefefe68c600e3050100000000000083ae000090008202f585030350040200020020210200000020020001151c07e302050000000105060000000006000000000600000000060000000006000000001c07e302060000000105060000000006000000000600000000060000000006000000001c07e302070000000105060000000006000000000600000000060000000006000000001c07e302080000000105060000000006000000000600000000060000000006000000001c07e302090000000105060000000006acc016");
        Frame698.Parser frame698_1 = new Frame698().parse("681100630501000000000000E9E40080D67816");
        Frame698.Parser frame698_2 = new Frame698().parse("fefefefe68c600e3050100000000000083ae01c0000000000600000000060000000006000000001c07e3020a0000000105060000000006000000000600000000060000000006000000001c07e3020b0000000105060000000006000000000600000000060000000006000000001c07e3020c0000000105060000000006000000000600000000060000000006000000001c07e3020d0000000105060000000006000000000600000000060000000006000000001c07e3020e0000000105060000000006000000000600dd3116");
        // Frame698 frame6981 = new Frame698().newBuilder().setLinkDataStr()
        DateTime dateTime = new DateTime("07E00513040805000089");
        System.out.println(dateTime.format() + "\n");
        //dateTime = new Time(-1,-1,3);

        System.out.println(frame698_0.toFormatString());
        System.out.println(frame698.toFormatString());
        System.out.println("------------" + frame698_0.reBuild());
        System.out.println(frame698_1.toFormatString());
        System.out.println("------------");
        System.out.println(frame698_2.toFormatString());
    }
}
