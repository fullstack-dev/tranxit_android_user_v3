# TRANXIT REVAMP NEW


##Installation Guide

```
git clone [PROJECT]
```

Delete ```.git``` folder in Project folder

Rename Package using refactor


## Getting Started

Build.gradle
```
applicationId "com.tranxit.app"
buildConfigField "String", "BASE_URL", '"http://schedule.deliveryventure.com/"'
buildConfigField "String", "CLIENT_SECRET", '"yVnKClKDHPcDlqqO1V05RtDRdvtrVHfvjlfqliha"'
buildConfigField "String", "CLIENT_ID", '"2"'
buildConfigField "String", "STRIPE_PK", '"xxxxxx"'
buildConfigField "String", "PAYPAL_CLIENT_TOKEN", '"xxxxxxxxxx"'
```


string.xml
```
<string name="app_name" translatable="false">Tranxit User</string>
<string name="google_map_key" translatable="false">AIzaSyAdXoVw-M-g4vgEOZZK7Dc9jMUlLR5xVXI</string>
<string name="FACEBOOK_APP_ID" translatable="false">227306957774573</string>
<string name="ACCOUNT_KIT_CLIENT_TOKEN" translatable="false">3f73b9bdd9f499561e32e834e7dcd1f8</string>
<string name="google_signin_server_client_id" translatable="false">405772036882-ib15rm0b0f5cq3mpqbljk9b28f6d0j4u.apps.googleusercontent.com</string>
```

[google_signin_server_client_id](https://console.cloud.google.com/) -API&Services -> Credentials -> OAuth 2.0 client IDs -> Web client (auto created by Google Service)



Add app in [firebase](http://console.firebase.google.com/), and don't forgot to add SHA-1

[Firebase console](http://console.firebase.google.com/) -Realtime Database -> set database rules as below
```
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```



