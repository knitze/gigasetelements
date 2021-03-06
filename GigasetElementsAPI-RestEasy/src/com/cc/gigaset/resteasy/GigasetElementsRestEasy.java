package com.cc.gigaset.resteasy;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import com.cc.gigaset.common.GigasetElementsBase;
import com.cc.gigaset.common.Listener;

public class GigasetElementsRestEasy
	extends GigasetElementsBase {

    public GigasetElementsRestEasy(String username, String password, List<Listener> listeners) {
	super(username, password, listeners);
    }

    public GigasetElementsRestEasy(String username, String password) {
	super(username, password);
    }

    @Override
    protected Client newClient() {
	ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder();
	ResteasyClient client = resteasyClientBuilder.establishConnectionTimeout(getTimeout(), TimeUnit.MILLISECONDS).socketTimeout(getTimeout(), TimeUnit.MILLISECONDS).build();
	AbstractHttpClient httpClient = (AbstractHttpClient) ((ApacheHttpClient4Engine) client.httpEngine()).getHttpClient();
	httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {

	    @Override
	    protected boolean isRedirectable(String method) {
		return true;
	    }
	});
	httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

	    @Override
	    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
		request.setParams(new AllowRedirectHttpParams(request.getParams()));
	    }
	});
	return client;
    }
}
