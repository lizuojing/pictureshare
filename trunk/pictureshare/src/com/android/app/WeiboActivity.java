package com.android.app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.app.api.WeiboApi.TokenManager;
import com.android.app.config.ApiConfig;
import com.android.app.utils.MyWebViewClient;
import com.android.app.view.PicWebView;
import com.renren.Renren;
import com.renren.Util;
import com.tencent.qzone.ClientHttpRequest;
import com.tencent.qzone.CommonException;
import com.tencent.qzone.ParseResoneJson;
import com.tencent.weibo.OAuth;
import com.tencent.weibo.OAuthClient;
import com.weibo.net.AccessToken;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Token;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboParameters;

public class WeiboActivity extends BaseActivity {
	protected static final String TAG = "WeiboActivity";

	private boolean currentStop = false;

	// 微博类型
	public final static String TYPE_SINA = "sina";
	public final static String TYPE_TENCENT = "tencent";
	public final static String TYPE_RENREN = "renren";
	public final static String TYPE_QZONE = "qzone";

	// sina
	// public final static String SINA_REQUEST_URL =
	// "chouti://request.weibo.cn";
	public final static String SINA_ACCESS_URL = "chouti://access.weibo.cn";
	public static final String DEFAULT_REDIRECT_URI = "http://dig.chouti.com/blank.html";
	public static final String TOKEN = "access_token";
	public static final String EXPIRES = "expires_in";
	private Token sinaToken;

	// qq
	public final static String QQ_Auth = "chouti://t.qq.com";
	public static OAuth oauth = new OAuth(OAuth.URL_ACTIVITY_CALLBACK); // 初始化OAuth请求令牌;
	public static OAuthClient auth = new OAuthClient();// OAuth 认证对象

	// renren
	private static final String[] RENREN_DEFAULT_PERMISSIONS = { "publish_feed", "create_album", "photo_upload",
			"read_user_album", "status_update" };

	// qzone
	public static String mAccessToken, mOpenId;
	public static final String Qzone_Auth = "auth://tauth.qq.com";
	public static final String CALLBACK = "tencentauth://auth.qq.com";

	public static final String INTENT_WEIBO_TYPE = "intent_weibo_type";

	//http://open.t.qq.com/cgi-bin/authorize?oauth_token=aa6cbf20d291423d9ca3e283c4f5b3e0&type=5&code=101&checkType=error
    private static final String TENCENT_DENY_TYPE = "type=5";//腾讯微博拒绝
    private static final String TENCENT_DENY_CODE = "code=101";//腾讯微博拒绝
    private static final String TENCENT_DENY_CHECKTYPE = "checkType=error";//腾讯微博拒绝
    private static final String SINA_DENY_CODE = "21330";
    private static final String SINA_DENY = "access_denied";

	public static String SCOPE = "get_user_info,get_user_profile,add_share,add_topic,list_album,upload_pic,add_album";// 授权范围

	private PicWebView webView;
	protected ProgressBar progressBar;
	private ImageView ivTitle;

	private Renren renren;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		webView = (PicWebView) findViewById(R.id.webView);
		ivTitle = (ImageView) findViewById(R.id.iv_title);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕宽度
		webView.requestFocus();
		webView.setWebChromeClient(webChromeClient);
		webView.setWebViewClient(webViewClient);

		String stringExtra = getIntent().getStringExtra(INTENT_WEIBO_TYPE);
		if (TYPE_SINA.equals(stringExtra)) {
			ivTitle.setImageResource(R.drawable.ic_weibo_title_sina);
			handleWeiboAuth();
		} else if (TYPE_TENCENT.equals(stringExtra)) {
			ivTitle.setImageResource(R.drawable.ic_weibo_title_tencent);
			handleTencent();
		} else if (TYPE_RENREN.equals(stringExtra)) {
			ivTitle.setImageResource(R.drawable.ic_weibo_title_renren);
			handleRenren();
		} else if (TYPE_QZONE.equals(stringExtra)) {
			ivTitle.setImageResource(R.drawable.ic_weibo_title_qzone);
			handleQzone(ApiConfig.APPID);
		}

	}
	
	@Override
	public void onBackPressed() {
	    String stringExtra = getIntent().getStringExtra(INTENT_WEIBO_TYPE);

	    super.onBackPressed();
	}

	private void handleQzone(String client_id) {
		String mGraphURL = ("https://graph.qq.com/oauth2.0/authorize?response_type=token&display=mobile&client_id=%s&scope=%s&redirect_uri=%s&status_userip=%s&status_os=%s&status_machine=%s&status_version=%s#" + System
				.currentTimeMillis());

		String url = String.format(mGraphURL, new Object[] { client_id, SCOPE, CALLBACK, getIp(), getOS(),
				getMachine(), getVersion() });

		webView.loadUrl(url);
	}

	private String getIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress())
						return inetAddress.getHostAddress().toString();
				}
			}
		} catch (SocketException ex) {
		}
		return "";
	}

	private String getOS() {
		return Build.VERSION.RELEASE;
	}

	private String getMachine() {
		return Build.MODEL;
	}

	private String getVersion() {
		return Build.VERSION.SDK;
	}

	private void handleRenren() {
		renren = new Renren(ApiConfig.RENREN_API_KEY, ApiConfig.RENREN_SECRET_KEY, ApiConfig.RENREN_APP_ID, this);
		authorize(null, Renren.DEFAULT_REDIRECT_URI, "token", ApiConfig.RENREN_API_KEY);
	}

	private void authorize(String[] permissions, String redirectUrl, String responseType, String apiKey) {
		// 调用CookieManager.getInstance之前
		// 必须先调用CookieSyncManager.createInstance
		CookieSyncManager.createInstance(this);

		Bundle params = new Bundle();
		params.putString("client_id", apiKey);
		params.putString("redirect_uri", redirectUrl);
		params.putString("response_type", responseType);
		params.putString("display", "touch");

		// 若开发者提供的权限列表为空，则使用默认权限列表
		if (permissions == null) {
			permissions = RENREN_DEFAULT_PERMISSIONS;
		}

		if (permissions != null && permissions.length > 0) {
			String scope = TextUtils.join(" ", permissions);
			params.putString("scope", scope);
		}

		String url = Renren.AUTHORIZE_URL + "?" + Util.encodeUrl(params);
		if (checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			Util.showAlert(this, "没有权限", "应用需要访问互联网的权限");
		} else {
			webView.loadUrl(url);
		}
	}

	private void handleTencent() {
	    String oauth_token = null;
	    String oauth_token_secret = null;
		try {
			oauth = auth.requestToken(oauth);
			oauth_token = oauth.getOauth_token();
			oauth_token_secret = oauth.getOauth_token_secret();
		} catch (Exception e) {
		}
		String urlString = "http://open.t.qq.com/cgi-bin/authorize?oauth_token=" + oauth_token;
		Log.i(TAG, "thread oauth_token is " + oauth_token + " oauth_token_secret " + oauth_token_secret);
		webView.loadUrl(urlString);

	}

	private void handleWeiboAuth() {
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(ApiConfig.Sina_APP_KEY, ApiConfig.Sina_APP_SECRET);
		// Oauth2.0
		weibo.setRedirectUrl("http://dig.chouti.com/blank.html");// 此处回调页内容应该替换为与appkey对应的应用回调页
		authorize(new String[] {});
	}

	/**
	 * 新浪认证
	 * 
	 * @param permissions
	 */
	public void authorize(String[] permissions) {
		// 新浪
		Utility.setAuthorization(new Oauth2AccessTokenHeader());

		WeiboParameters params = new WeiboParameters();
		if (permissions.length > 0) {
			params.add("scope", TextUtils.join(",", permissions));
		}
		CookieSyncManager.createInstance(this);
		params.add("display", "mobile");
		params.add("client_id", ApiConfig.Sina_APP_KEY);
		params.add("response_type", "token");
		params.add("redirect_uri", DEFAULT_REDIRECT_URI);
		params.add("display", "mobile");
		String url = Weibo.URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(params);
		if (checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			Utility.showAlert(this, "Error", "Application requires permission to access the Internet");
		} else {
			webView.loadUrl(url);
		}
	}

	private boolean parseResult(String url) {
	    boolean result = false;
		String tmp = url;
		if (tmp.startsWith(CALLBACK + "?#"))
			tmp = tmp.substring(tmp.indexOf('#') + 1);
		else {
			tmp = tmp.substring(tmp.indexOf('?') + 1);
		}
		String[] arr = tmp.split("&");
		HashMap<String, String> res = new HashMap<String, String>();
		for (String item : arr) {
			String[] data = item.split("=");
			res.put(data[0], data[1]);
		}
		final String accessToken = ((String) res.get("access_token"));
		if (accessToken != null) {
		    String openIdUrl = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s", new Object[] { accessToken });
		    int ret = 0;
		    String msg = null;
		    try {
                String resp = ClientHttpRequest.openUrl(openIdUrl, "GET", null);
                JSONObject obj = ParseResoneJson.parseJson(resp);
                Log.i(TAG, "resp is " + resp);
                try {
					ret = obj.getInt("error");
					msg = obj.getString("error_description");
				} catch (Exception e) {
				}
                if (ret == 0) {
                    result = true;
                    String openid = obj.getString("openid");
                    Log.i(TAG, "openid is " + openid);
                    TokenManager.getInstance(WeiboActivity.this).add(Qzone_Auth, accessToken,openid);
                } else {
                	Log.i(TAG, "ret is " + ret + " msg is " + msg);
                }
            } catch (Exception e) {
            } catch (CommonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return result;
	}

	
	
	WebViewClient webViewClient = new MyWebViewClient() {

		@Override
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			if (progressBar != null)
				progressBar.setVisibility(View.GONE);
			super.doUpdateVisitedHistory(view, url, isReload);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i(TAG, "shouldOverrideUrlLoading is running");
			return handleUrl(url);
		}

		private boolean handleUrl(String url) {
			Log.i(TAG, "handleUrl is " + url);
		    boolean result = false;
			if (url.startsWith(DEFAULT_REDIRECT_URI)) { // 新浪返回
				CookieSyncManager.getInstance().sync();
				if (null == sinaToken) {
					sinaToken = new Token();
				}
				Bundle values = Utility.parseUrl(url);

				String error = values.getString("error");
				String error_code = values.getString("error_code");

				if (error == null && error_code == null) {
					sinaToken.setToken(values.getString(TOKEN));
					sinaToken.setExpiresIn(values.getString(EXPIRES));
					AccessToken accessToken = new AccessToken(sinaToken.getToken(), ApiConfig.Sina_APP_SECRET);
					accessToken.setExpiresIn(sinaToken.getExpiresIn());
					Weibo.getInstance().setAccessToken(accessToken);
					if (Weibo.getInstance().isSessionValid()) {
						Log.i(TAG,
								"Login Success! access_token=" + sinaToken.getToken() + " expires="
										+ sinaToken.getExpiresIn());
						TokenManager.getInstance(WeiboActivity.this).add(SINA_ACCESS_URL, accessToken.getToken(),
								accessToken.getSecret());
						Toast.makeText(WeiboActivity.this, getResources().getString(R.string.weibo_sina_bind), Toast.LENGTH_SHORT).show();
						result = false;
						finish();
					} else {
						Log.i(TAG, "Failed to receive access token");
					}
				}else {
				    if(SINA_DENY.equals(error)&&SINA_DENY_CODE.equals(error_code)) {
				        result = false;
				        finish();
				    }
				}
			} else if (url.startsWith(OAuth.URL_ACTIVITY_CALLBACK)) {
				Uri uri = Uri.parse(url);
				String from = uri.getQueryParameter("from");
				String verifier = uri.getQueryParameter("oauth_verifier");
				String token = uri.getQueryParameter("oauth_token");
				if ("tencent".equals(from)) { // 腾讯返回
					oauth.setOauth_verifier(verifier);
					oauth.setOauth_token(token);
					try {
						// 获取request token
						oauth = auth.accessToken(oauth);
						Log.d(TAG, "onPageBegin oauth_token is " + oauth.getOauth_token() + " oauth_token_secret "
								+ oauth.getOauth_token_secret());
						TokenManager.getInstance(WeiboActivity.this).add(QQ_Auth, oauth.getOauth_token(),
								oauth.getOauth_token_secret());
						Toast.makeText(WeiboActivity.this, getResources().getString(R.string.weibo_tencent_bind), Toast.LENGTH_SHORT).show();
						result = true;
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if(url.startsWith("http://open.t.qq.com/cgi-bin/authorize")){
			    if(url.contains(TENCENT_DENY_TYPE)&&url.contains(TENCENT_DENY_CODE)&&url.contains(TENCENT_DENY_CHECKTYPE)) {
                    result = false;
                    finish();
                }
            } else if (url.startsWith("tencentauth://")) {
				Log.i(TAG, "url is " + url);
				result = parseResult(url);
				Toast.makeText(WeiboActivity.this, getResources().getString(R.string.weibo_qzone_bind), Toast.LENGTH_SHORT).show();

				finish();
			} else if (url.startsWith(Renren.DEFAULT_REDIRECT_URI)) {
				Bundle values = Util.parseUrl(url);
				String accessToken = values.getString("access_token");
				if (accessToken != null) {
					CookieSyncManager.getInstance().sync();
					if (accessToken != null) {
						Log.i(TAG, "Success obtain access_token=" + accessToken);
						result = true;
						Toast.makeText(WeiboActivity.this, getResources().getString(R.string.weibo_renren_bind), Toast.LENGTH_SHORT).show();
						try {
							renren.updateAccessToken(accessToken);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					finish();
				}
			}
            return result;
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			if (!currentStop) {
				super.onLoadResource(view, url);
			}
		}

		@Override
		public void onScaleChanged(WebView view, float oldScale, float newScale) {
			super.onScaleChanged(view, oldScale, newScale);
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.i(TAG, "onPageStarted load url is " + url);
			handleUrl(url);
		};

		public void onPageFinished(WebView view, String url) {
			Log.i(TAG, "onPageStarted load url is " + url);
		}

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
        	Log.i(TAG, "errorCode is " + errorCode + " description is " + description + " /r/n " + " failingUrl is " + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,
                String realm) {
        	Log.i(TAG, "host is " + host + " realm is " + realm);
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        };
		
		

	};

	WebChromeClient webChromeClient = new WebChromeClient() {

		public void onProgressChanged(WebView view, int progress) {
			progressBar.setMax(100);
			if (progress < 100) {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(progress);

			} else {
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, progress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
		}

	};

	@Override
	protected void onDestroy() {
		currentStop = true;
		if(webView!=null) {
		    webView.stopLoading();
		}
		super.onDestroy();
	}

}
