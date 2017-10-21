package com.shishir.sieveoferatosthenes.data;

/**
 * Created by Shishir on 10/21/2017.
 */

public class SieveNum {

    int num;
    boolean flag;

    public SieveNum(int num, boolean flag) {
        this.num = num;
        this.flag = flag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "SieveNum{" + "num=" + num + ", flag=" + flag + '}';
    }
}
