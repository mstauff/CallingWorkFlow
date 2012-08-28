package org.lds.community.CallingWorkFlow.api;

import android.content.SharedPreferences;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lds.community.CallingWorkFlow.domain.Calling;
import org.lds.community.CallingWorkFlow.domain.Member;
import org.lds.community.CallingWorkFlow.domain.Position;
import org.lds.community.CallingWorkFlow.domain.WorkFlowStatus;
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

    private static final String ROOT = "http://10.0.2.2:8080";
    private static final String WARD_LIST_URL = ROOT + "/wardList";
    private static final String POSITION_LIST_URL = ROOT + "/callingIds";
    private static final String STATUSES_URL = ROOT + "/statuses";
    private static final String CALLINGS_PENDING_URL = ROOT + "/callings/pending";
    private static final String CALLINGS_COMPLETED_URL = ROOT + "/callings/completed";
    private static final String CALLING_UPDATE_URL = ROOT + "/calling/%d/update?callingId=%d&status=%s";

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

    public Calling updateCalling( Calling calling ) {
        Calling callingResult = null;
        try {
            String url = String.format( CALLING_UPDATE_URL, calling.getIndividualId(), calling.getPositionId(), calling.getStatusName() );
            String jsonResult = executeGetJSONRequest( new HttpGet( url ) );
            callingResult = JSONUtil.parseCalling(new JSONObject(jsonResult));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return callingResult;
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

    public List<Position> getPositionIds() {
        List<Position> positionList = new ArrayList<Position>();
        try {
            String positionJSON = executeGetJSONRequest( new HttpGet( POSITION_LIST_URL ) );
            positionList = JSONUtil.parsePositionIds(new JSONArray(positionJSON));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return positionList;
    }

    public List<WorkFlowStatus> getStatuses() {
        List<WorkFlowStatus> statusList = new ArrayList<WorkFlowStatus>();
        try {
            String json = executeGetJSONRequest( new HttpGet( STATUSES_URL ) );
            statusList = JSONUtil.parseStatuses(new JSONArray(json));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return statusList;
    }

    public List<Calling> getPendingCallings( ) {
        return getCallings( CallingGroup.PENDING );
    }

    public List<Calling> getCompletedCallings( ) {
        return getCallings( CallingGroup.COMPLETED );
    }

    private List<Calling> getCallings( CallingGroup callingGroup ) {
        List<Calling> callingList = new ArrayList<Calling>();
        try {
            String url = callingGroup == CallingGroup.PENDING ? CALLINGS_PENDING_URL : CALLINGS_COMPLETED_URL;
            String json = executeGetJSONRequest( new HttpGet( url ) );
            callingList = JSONUtil.parseCallings(new JSONArray(json));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return callingList;
    }

    private enum CallingGroup { PENDING, COMPLETED };
}
