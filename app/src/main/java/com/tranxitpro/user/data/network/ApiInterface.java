package com.tranxitpro.user.data.network;

import com.appoets.paytmpayment.PaytmObject;
import com.tranxitpro.user.data.network.model.AddWallet;
import com.tranxitpro.user.data.network.model.AddressResponse;
import com.tranxitpro.user.data.network.model.BrainTreeResponse;
import com.tranxitpro.user.data.network.model.CancelResponse;
import com.tranxitpro.user.data.network.model.Card;
import com.tranxitpro.user.data.network.model.CheckSumData;
import com.tranxitpro.user.data.network.model.CheckVersion;
import com.tranxitpro.user.data.network.model.Coupon;
import com.tranxitpro.user.data.network.model.DataResponse;
import com.tranxitpro.user.data.network.model.Datum;
import com.tranxitpro.user.data.network.model.DisputeResponse;
import com.tranxitpro.user.data.network.model.EstimateFare;
import com.tranxitpro.user.data.network.model.ForgotResponse;
import com.tranxitpro.user.data.network.model.Help;
import com.tranxitpro.user.data.network.model.Message;
import com.tranxitpro.user.data.network.model.NotificationManager;
import com.tranxitpro.user.data.network.model.PromoResponse;
import com.tranxitpro.user.data.network.model.Provider;
import com.tranxitpro.user.data.network.model.RegisterResponse;
import com.tranxitpro.user.data.network.model.Rental;
import com.tranxitpro.user.data.network.model.Service;
import com.tranxitpro.user.data.network.model.SettingsResponse;
import com.tranxitpro.user.data.network.model.Token;
import com.tranxitpro.user.data.network.model.User;
import com.tranxitpro.user.data.network.model.WalletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("nearbysearch/json?key=AIzaSyCRLa4LQZWNQBcjCYcIVYA45i9i8zfClqc&sensor=true&radius=50")
    Observable<Object> doPlaces(@Query(value = "type", encoded = true) String type,
                                @Query(value = "location", encoded = true) String location,
                                @Query(value = "keyword", encoded = true) String key);

    @FormUrlEncoded
    @POST("api/user/checkversion")
    Observable<CheckVersion> checkversion(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/signup")
    Observable<RegisterResponse> register(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/auth/google")
    Observable<Token> loginGoogle(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/auth/facebook")
    Observable<Token> loginFacebook(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/oauth/token")
    Observable<Token> login(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("/oauth/token")
    Observable<Token> refreshLogin(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/forgot/password")
    Observable<ForgotResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("api/user/reset/password")
    Observable<Object> resetPassword(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/verify")
    Observable<Object> verifyEmail(@Field("email") String email);

    @GET("api/user/details")
    Observable<User> profile();

    @FormUrlEncoded
    @POST("api/user/logout")
    Observable<Object> logout(@Field("id") String id);

    @FormUrlEncoded
    @POST("api/user/change/password")
    Observable<Object> changePassword(@FieldMap HashMap<String, Object> params);

    @GET("api/user/request/check")
    Observable<DataResponse> checkStatus();

    @GET("api/user/show/providers")
    Observable<List<Provider>> providers(@QueryMap HashMap<String, Object> params);

    @Multipart
    @POST("api/user/update/profile")
    Observable<User> editProfile(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part filename);

    @GET("api/user/services")
    Observable<List<Service>> services();

    @GET("api/user/rental/logic")
    Observable<List<Rental>> rentals();

    /*@GET("api/user/estimated/fare")
    Observable<EstimateFare> estimateFare(@QueryMap Map<String, Object> params);*/

    @GET("api/user/estimated/fare")
    Call<EstimateFare> estimateFare(@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/send/request")
    Observable<Object> sendRequest(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/cancel/request")
    Observable<Object> cancelRequest(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("/api/user/update/request")
    Observable<Object> updateRequest(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/payment")
    Observable<Message> payment(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("user/api/payment/success")
    Observable<Object> paymentSuccess(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/rate/provider")
    Observable<Object> rate(@FieldMap HashMap<String, Object> params);

    @GET("api/user/trips")
    Observable<List<Datum>> pastTrip();

    @GET("api/user/trip/details")
    Observable<List<Datum>> pastTripDetails(@Query("request_id") Integer requestId);

    @GET("api/user/upcoming/trips")
    Observable<List<Datum>> upcomingTrip();

    @GET("api/user/upcoming/trip/details")
    Observable<List<Datum>> upcomingTripDetails(@Query("request_id") Integer requestId);

    @FormUrlEncoded
    @POST("api/user/dispute")
    Observable<Object> dispute(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/dispute-list")
    Observable<List<DisputeResponse>> getDispute(@Field("dispute_type") String dispute_type);

    @FormUrlEncoded
    @POST("api/user/drop-item")
    Observable<Object> dropItem(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/card")
    Observable<Object> card(@Field("stripe_token") String stripeToken);

    @GET("api/user/card")
    Observable<List<Card>> card();

    @GET("api/user/braintree/token")
    Observable<BrainTreeResponse> getBraintreeToken();

    @FormUrlEncoded
    @POST("api/user/card/destroy")
    Observable<Object> deleteCard(@Field("card_id") String cardId, @Field("_method") String method);

    @FormUrlEncoded
    @POST("api/user/add/money")
    Observable<AddWallet> addMoney(@FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST("api/user/add/money")
    Observable<PaytmObject> addMoneyPaytm(@FieldMap HashMap<String, Object> params);

    @GET("api/user/help")
    Observable<Help> help();

    @FormUrlEncoded
    @POST("api/user/promocode/add")
    Observable<Object> coupon(@Field("promocode") String promoCode);

    @GET("api/user/promocodes")
    Observable<List<Coupon>> coupon();

    @FormUrlEncoded
    @POST("api/user/location")
    Observable<Object> addAddress(@FieldMap HashMap<String, Object> params);

    @GET("api/user/location")
    Observable<AddressResponse> address();

    @DELETE("api/user/location" + "/" + "{id}")
    Observable<Object> deleteAddress(@Path("id") Integer id);

    @GET("api/user/wallet/passbook")
    Observable<WalletResponse> wallet();

    @GET("api/user/promocodes_list")
    Observable<PromoResponse> promocodesList();

    @FormUrlEncoded
    @POST("/api/user/chat")
    Observable<Object> postChatItem(
            @Field("sender") String sender,
            @Field("user_id") String user_id,
            @Field("message") String message);

    @FormUrlEncoded
    @POST("/api/user/update/language")
    Observable<Object> postChangeLanguage(@Field("language") String language);

    @FormUrlEncoded
    @POST("api/user/verify-credentials")
    Observable<Object> verifyCredentials(@Field("mobile") String mobileNumber, @Field("country_code") String countryCode);

    @GET("api/user/reasons")
    Observable<List<CancelResponse>> getCancelReasons();

    @GET("/api/user/settings")
    Observable<SettingsResponse> getSettings();

    @GET("/api/user/notifications/user")
    Observable<List<NotificationManager>> getNotificationManager();

    @FormUrlEncoded
    @POST("api/user/extend/trip")
    Observable<Object> extendTrip(@FieldMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST("api/user/payu/response")
    Observable<CheckSumData> payuMoneyChecksum();

    @FormUrlEncoded
    @POST("api/user/payment")
    Observable<PaytmObject> payTmChecksum(@Field("request_id")String request_id,@Field("payment_mode")String payment_mode);
}
