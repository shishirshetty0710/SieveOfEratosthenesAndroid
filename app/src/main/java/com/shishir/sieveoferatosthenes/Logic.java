package com.shishir.sieveoferatosthenes;

import com.shishir.sieveoferatosthenes.data.SieveNum;

import java.util.ArrayList;

/**
 * Created by Shishir on 10/21/2017.
 */

public class Logic {

    public static ArrayList<SieveNum> processGrid(int num, ArrayList<SieveNum> sieveNumArrayList) {

        for (int i = 2; i < Math.ceil(Math.sqrt(num)); i++) {

            if (sieveNumArrayList.get(i).isFlag()) {

                for (int k = 0; k < num; k++) {

                    int j = i * i + k * i;

                    if (j < num) {
                        sieveNumArrayList.get(j).setFlag(false);
                    }
                }

            }
        }
        sieveNumArrayList.remove(0);
        sieveNumArrayList.remove(0);

        return sieveNumArrayList;
    }
}
