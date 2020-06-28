package com.accentrs.apilibrary.callback;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.accentrs.apilibrary.R;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.apilibrary.utils.SessionSharedPreference;
import com.accentrs.apilibrary.utils.Utils;
import com.accentrs.apilibrary.volley.JsonCachceRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Results extends VolleyClass {

    public static final String TAG = Results.class.getSimpleName();

    private Context mContext;
    private ResultsCallBack mResultsCallBack;


    public Results(Context applicationContext) {
        super(applicationContext);
        this.mContext = applicationContext;
    }

    /**
     * @param mResultsCallBack
     * @return
     */

    public void setOnResultsCallBack(ResultsCallBack mResultsCallBack) {
        this.mResultsCallBack = mResultsCallBack;
    }


    /**
     * Sets only string response type
     *
     * @param response
     */
    private void setResponseTypeString(String response) {
        ResponseType responseType = new ResponseType();
        responseType.setStringResponse(response.toString());
        mResultsCallBack.onSuccess(responseType);
    }


    /**
     * Sets error message
     *
     * @param error
     */
    private void setErrorMessage(VolleyError error) {
        String message = Utils.setErrorMessage(error.networkResponse);
        if (message != null) {
            mResultsCallBack.onError(message);
        } else {
            mResultsCallBack.onError(mContext.getResources().getString(R.string.something_went_wrong_text));
        }
    }


    public void company() {

        StringRequest mCompanyRequest = new StringRequest(Request.Method.GET, Constants.COMPANY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getApplicationHeaderType();
            }
        };
        mCompanyRequest.setShouldCache(false);
        addVolleyRequest(mCompanyRequest, Results.class.getSimpleName(), false, this);

    }

    public void companyDetail(String offerId) {
        JsonCachceRequest offerDetailCacheRequest = new JsonCachceRequest(Request.Method.GET, Constants.COMPANY_DETAIL_URL + offerId, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {

                    String networkData = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));

                    setResponseTypeString(networkData);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String s = error.getMessage();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };

        addVolleyRequest(offerDetailCacheRequest, Results.class.getSimpleName(), false, this);

    }

    public void homeOffers() {

        StringRequest mHomeOfferRequest = new StringRequest(Request.Method.GET, Constants.HOME_OFFERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mHomeOfferRequest.setShouldCache(true);
        addVolleyRequest(mHomeOfferRequest, Results.class.getSimpleName(), false, this);

    }

    public void categoryOffers() {

        StringRequest mHomeOfferRequest = new StringRequest(Request.Method.GET, Constants.CATEGORY_HOME_OFFERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mHomeOfferRequest.setShouldCache(true);
        addVolleyRequest(mHomeOfferRequest, Results.class.getSimpleName(), false, this);

    }

    public void companyOffers(String companyOfferUrl) {

        StringRequest mCompanyOfferRequest = new StringRequest(Request.Method.GET, Constants.COMPANY_OFFERS_URL + companyOfferUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mCompanyOfferRequest.setShouldCache(true);
        addVolleyRequest(mCompanyOfferRequest, Results.class.getSimpleName(), false, this);

    }


    public void categoryOffers(String categoryOfferUrl) {

        StringRequest mCategoryOfferRequest = new StringRequest(Request.Method.GET, Constants.CATEGORY_OFFERS_URL + categoryOfferUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mCategoryOfferRequest.setShouldCache(true);
        addVolleyRequest(mCategoryOfferRequest, Results.class.getSimpleName(), false, this);

    }


    public void userBookmarkApi() {

        StringRequest mUserBookmarkRequest = new StringRequest(Request.Method.GET, Constants.USER_BOOKMARK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mUserBookmarkRequest.setShouldCache(true);
        addVolleyRequest(mUserBookmarkRequest, Results.class.getSimpleName(), false, this);

    }

    public void searchOfferApi(String searchText) {

        StringRequest mSearchRequest = new StringRequest(Request.Method.GET, Constants.SEARCH_OFFER_URL + searchText, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sqwerty", response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mSearchRequest.setShouldCache(true);
        addVolleyRequest(mSearchRequest, Results.class.getSimpleName(), false, this);

    }

    public void addUserBookmarkApi(final JSONObject bookmarkJsonObject) {

        StringRequest mUserBookmarkRequest = new StringRequest(Request.Method.POST, Constants.USER_BOOKMARK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return bookmarkJsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mUserBookmarkRequest.setShouldCache(true);
        addVolleyRequest(mUserBookmarkRequest, Results.class.getSimpleName(), false, this);

    }

    public void removeUserBookmarkApi(String offerId) {

        StringRequest mUserBookmarkRequest = new StringRequest(Request.Method.DELETE, Constants.USER_BOOKMARK_URL + Constants.FORWARD_SLASH + offerId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mUserBookmarkRequest.setShouldCache(true);
        addVolleyRequest(mUserBookmarkRequest, Results.class.getSimpleName(), false, this);

    }

    public void addUserGCMToken(final JSONObject mUserGCMDeviceJsonObject) {
        StringRequest mUserDeviceRequest = new StringRequest(Request.Method.POST, Constants.ADD_USER_GCM_DEVICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mUserGCMDeviceJsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getApplicationHeaderType();
            }
        };
        mUserDeviceRequest.setShouldCache(false);
        addVolleyRequest(mUserDeviceRequest, Results.class.getSimpleName(), false, this);
    }

    public void userOfferView(final JSONObject mUserOfferViewJsonObject) {
        StringRequest mUserDeviceRequest = new StringRequest(Request.Method.POST, Constants.USER_OFFER_VIEW_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mUserOfferViewJsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mUserDeviceRequest.setShouldCache(false);
        addVolleyRequest(mUserDeviceRequest, Results.class.getSimpleName(), false, this);
    }

    public void fetchSpinningWheelGame() {

        StringRequest mSpinningWheelGameRequest = new StringRequest(Request.Method.GET, Constants.SPINNING_WHEEL_GAME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nithi", response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mSpinningWheelGameRequest.setShouldCache(false);
        addVolleyRequest(mSpinningWheelGameRequest, Results.class.getSimpleName(), false, this);

    }

    public void fetchAdsPopup() {


        StringRequest mAdsPopupRequest = new StringRequest(Request.Method.GET, Constants.ADS_POPUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nithin", response);
                setResponseTypeString(response);
                //Toast.makeText(mContext, "working", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mAdsPopupRequest.setShouldCache(false);
        addVolleyRequest(mAdsPopupRequest, Results.class.getSimpleName(), false, this);

    }


    public void viewAdsPopup(final JSONObject viewAdsPopupJsonObject) {

        StringRequest mAdsPopupRequest = new StringRequest(Request.Method.POST,
                Constants.ADS_POPUP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return viewAdsPopupJsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mAdsPopupRequest.setShouldCache(false);
        addVolleyRequest(mAdsPopupRequest, Results.class.getSimpleName(), false, this);

    }

    public void playSpinningWheel(final JSONObject mSpinningWheelJsonObject) {

        StringRequest mSpinningWheelRequest = new StringRequest(Request.Method.POST, Constants.SPINNING_WHEEL_GAME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mSpinningWheelJsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mSpinningWheelRequest.setShouldCache(false);
        addVolleyRequest(mSpinningWheelRequest, Results.class.getSimpleName(), false, this);

    }

    public void gameWinner() {

        StringRequest mGameWinnerRequest = new StringRequest(Request.Method.GET, Constants.GAME_WINNER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getUserApplicationHeaderType();
            }
        };
        mGameWinnerRequest.setShouldCache(true);
        addVolleyRequest(mGameWinnerRequest, Results.class.getSimpleName(), false, this);

    }

    public void couponCategory() {

        StringRequest mCouponCategoryRequest = new StringRequest(Request.Method.GET, Constants.COUPON_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setResponseTypeString(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //return getApplicationHeaderType();
                return null;
            }
        };
        mCouponCategoryRequest.setShouldCache(false);
        addVolleyRequest(mCouponCategoryRequest, Results.class.getSimpleName(), false, this);
    }

    public HashMap<String, String> getApplicationHeaderType() {

        HashMap<String, String> header = new HashMap<>();
        header.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        header.put(Constants.AUTHORIZATION_KEY, "iBu8g7w6Mg7SeLhEndU4wzbTqbSgNM4e");

        return header;
    }

    public HashMap<String, String> getUserApplicationHeaderType() {

        HashMap<String, String> header = new HashMap<>();
        header.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        header.put(Constants.AUTHORIZATION_KEY, "iBu8g7w6Mg7SeLhEndU4wzbTqbSgNM4e");
//        header.put(Constants.USER_KEY,"iob-15329714457");
        header.put(Constants.USER_KEY, new SessionSharedPreference(mContext).getUserId());
        return header;
    }

    public interface ResultsCallBack {
        void onSuccess(ResponseType response);

        void onError(String error);
    }
}
