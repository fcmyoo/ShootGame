package com.tarena.shoot;

//奖励
public interface Award {
    public int DOUBLE_FIRE = 0; //火力
    public int LIFE = 1;  //生命
//    获取奖励类型返回0为火力,返回1为生命
    public int getType();
}
