package com.heiketu.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class BuilderUser {

    @Test
    public void test01(){
        Md5Hash md = new Md5Hash("123456");
        md.setIterations(1);
        System.out.println(md.toString());
    }

}
