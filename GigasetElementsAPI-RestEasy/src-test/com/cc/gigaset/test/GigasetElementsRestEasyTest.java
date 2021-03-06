package com.cc.gigaset.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cc.gigaset.GigasetElements;
import com.cc.gigaset.common.Base;
import com.cc.gigaset.common.Mode;
import com.cc.gigaset.resteasy.GigasetElementsRestEasy;

public class GigasetElementsRestEasyTest {

    private static String username = "your.email@somewhere.com";
    private static String password = "thisismysecretpassword";

    @Before
    public void loadCredentials() throws Exception {
	Properties props = new Properties();
	File file = new File("gigasetelements.properties");
	Assert.assertTrue(file.exists());
	props.load(new FileInputStream(file));
	username = props.getProperty("username");
	password = props.getProperty("password");
    }

    @Test
    public void test() throws Exception {
	GigasetElementsRestEasy.DEBUG.set(true);
	GigasetElements service = new GigasetElementsRestEasy(username, password);
	Base base = service.setEventsCount(10).getBase();
	Mode mode = base.getMode();
	service.setMode(base, Mode.CUSTOM);
	service.setMode(base, mode);
    }
}
