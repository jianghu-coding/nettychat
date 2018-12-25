/*
 * Project: com.im.nettychat.util
 * 
 * File Created at 2018/12/24
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.util;

import java.util.Scanner;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/24 下午10:29
 */
public class Testd {

    public static void main(String[] args) {
        new Thread(new T()).start();
    }

}
class T implements Runnable {

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            start();
        }
    }

    private void start() {
        Scanner scanner = new Scanner(System.in);
        String rs = scanner.nextLine();
        if (rs.equals("0")) {
            Thread.currentThread().interrupt();
        }
    }
}
