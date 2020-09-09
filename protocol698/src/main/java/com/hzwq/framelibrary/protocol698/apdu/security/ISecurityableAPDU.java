package com.hzwq.framelibrary.protocol698.apdu.security;

import com.hzwq.framelibrary.protocol698.apdu.IAPDU;

/**
 * Created by qinling on 2019/4/23 11:23
 * Description: 可以转为安全传输的帧
 */
public interface ISecurityableAPDU extends IAPDU {
    SecurityAPDU security ();

}
