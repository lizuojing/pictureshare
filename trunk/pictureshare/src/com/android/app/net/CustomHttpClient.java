package com.android.app.net;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.android.app.PicApp;

public class CustomHttpClient extends DefaultHttpClient {

//	private static final String TAG = "CustomHttpClient";
	
	public static final int HTTP_CONNECTION_TIMEOUT = 10*1000;
	public static final int HTTP_SOCKET_TIMEOUT = 20*1000;

	private static CustomHttpClient httpClient;

	private CustomHttpClient() {
		super();
	}

	private CustomHttpClient(ClientConnectionManager conman, HttpParams params) {
		super(conman, params);
	}

	private CustomHttpClient(HttpParams params) {
		super(params);
	}

	public static synchronized HttpClient getInstance() {
		if (httpClient == null) {
			initHttpClient();
		}
		try {
			APN apn = APN.getPreferAPN(PicApp.getAppContext());
			if (apn != null && apn.hasProxy()) {
				HttpHost proxy = new HttpHost(apn.proxy, apn.port);
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			} else {
				httpClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpClient;
	}

	private static void initHttpClient() {
		BasicHttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

		HttpConnectionParams.setConnectionTimeout(params, HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, HTTP_SOCKET_TIMEOUT);

		ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(100));
		ConnManagerParams.setMaxTotalConnections(params, 100);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		httpClient = new CustomHttpClient(conMgr, params);
	}
}
