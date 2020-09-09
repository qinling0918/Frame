package com.hzwq.framelibrary.protocol698.data.datahelpers.parser;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.DAR;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DataManager;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.manager.DarManager;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/22 11:20
 * Description:  GetResult 是 choice类型
 */
public class AResultNormal implements IHex {
    private OAD oad;
    private GetResult getResult;

    private  String darStr ;
    private Data data;

    public AResultNormal(OAD oad, GetResult getResult) {
        this.oad = oad;
        this.getResult = getResult;
    }

    public AResultNormal(String hexStr) {
        int len = hexStr.length();
        if (len %2!=0 || !NumberConvert.isHexStr(hexStr)){
            throw new IllegalArgumentException("参数异常,偶数位16进制字符串");
        }
        if (len >=12 ){
            oad = new OAD(hexStr.substring(0,8));
            String type_dar_data = hexStr.substring(8,10); // 是 DAR 还是 Data
            String type = hexStr.substring(10,12); // DAR 或者 Data 的type
            if (type_dar_data.equalsIgnoreCase("00")){  //DAR
                 darStr = DarManager.getInstance().getStatusStr(type);
            }else if (type_dar_data.equalsIgnoreCase("01")){ //  Data
                Class clazz = DataManager.getInstance().getDataClass(type);
                int dataLength = DataManager.getInstance().getDataByteSize(type);
                if (dataLength>=0){
                    if (len>= 12+ dataLength){

                       /* try {
                            data = (Data) clazz.getConstructor(new Class[]{String.class}).newInstance(new String(hexStr.substring(12,12+ dataLength)));
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }*/
                       //   ReflectUtils.reflect(clazz).newInstance(hexStr.substring(12,12+ dataLength)).get();

                    }
                }

            }
              //  ReflectUtils.reflect(OAD.class).newInstance(hexStr.substring(10,18));

        }
    }

    @Override
    public String toHexString() {
        return null;
    }
}
