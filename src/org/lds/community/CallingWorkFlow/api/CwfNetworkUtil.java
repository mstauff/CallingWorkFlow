package org.lds.community.CallingWorkFlow.api;

import android.content.SharedPreferences;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.mobile.util.NetworkUtil;
import org.lds.mobile.util.TagUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for communicating with the server.
 */
@Singleton
public class CwfNetworkUtil {

    private static final String TAG = TagUtil.createTag("lds-cwf",CwfNetworkUtil.class);

    private AbstractHttpClient httpClient;

    @Inject
    private SharedPreferences preferences;

    private static final String DOMAIN = "http://10.0.2.2:8080";
    private static final String WARD_LIST_URL = DOMAIN + "/wardList";
    private static final String CALLING_IDS_URL = DOMAIN + "/callingIds";
    private static final String STATUSES_URL = DOMAIN + "/statuses";
    private static final String CALLINGS_PENDING_URL = DOMAIN + "/callings/pending";
    private static final String CALLINGS_COMPLETED_URL = DOMAIN + "/callings/completed";
    private static final String CALLING_UPDATE_URL = DOMAIN + "/calling/%d/update?callingId=%d&status=%s";

    public CwfNetworkUtil() {
    }

    /**
     * Configures the httpClient to connect to the URL provided.
     *
     * @return HttpClient
     */
    public HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = NetworkUtil.createHttpClient();
        }

        return httpClient;
    }

    private String executeGetJSONRequest(HttpGet getMethod) throws IOException {
        return NetworkUtil.executeGetJSONRequest(getHttpClient(), getMethod);
    }

    private HttpResponse executeGetRequest(HttpGet getMethod) {

        HttpResponse response = NetworkUtil.executeGetRequest(getHttpClient(), getMethod);

        if (!NetworkUtil.isResponseOK(response)) {
            if (response == null) {
                throw new ServiceException(ServiceException.ServiceError.LOST_CONNECTION, "executeRequest(...) error.  Unable to connect");
            } else {
                throw new ServiceException(ServiceException.ServiceError.SERVICE_UNAVAILABLE,
                        "Unable to Connect to www.lds.org. Code: " + response.getStatusLine().toString());
            }
        }

        return response;
    }

    public List<Member> getWardList() {
        List<Member> wardList = new ArrayList<Member>();
        try {
            String memberList = executeGetJSONRequest( new HttpGet( WARD_LIST_URL ) );
            wardList = JSONUtil.parseMemberList(new JSONArray(memberList));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return wardList;
    }
}
