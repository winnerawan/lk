package id.biz.wonderwall.ibod.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by winnerawan on 1/19/17.
 */

public class AppRequest {

    public Retrofit Request() {

        /**
         * Proxy Authenticator
         * Using proxy because openload blocked @indonesia
         * @param auth_user
         * @param auth_pass
         */

        final String auth_user = "";
        final String auth_pass = "";

        Authenticator proxyAuthenticator = new Authenticator() {
            @Override public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(auth_user, auth_pass);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }
        };

        /**
         * Set proxy host & port for retrofit client
         * @param proxyHost
         * @param proxyPort
         */
        final String proxyHost = "";
        final int proxyPort = ;
        java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress(proxyHost, proxyPort));
        // add logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).proxyAuthenticator(proxyAuthenticator).addInterceptor(loggingInterceptor).build();


        /**
         * Build Retrofit Request
         * too lazy for writing code @every activity
         */
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(AppConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client);
        Retrofit retrofit = builder.build();

        return retrofit;
    }
}
