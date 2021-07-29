package com.xunixianshi.filelibs.bean;

public class AuthLoginBean {
    private String access_token;//授权令牌
    private String token_type;//固定值：bearer
    private String refresh_token;//刷新令牌
    private long expires_in; //有效时间
    private String scope;
    private String custom_info_name;
    private String user_id;
    private String jti;
    private int languageIndex;//0 跟随系统 1中文 2 英文

    public int getLanguageIndex() {
        return languageIndex;
    }

    public void setLanguageIndex(int languageIndex) {
        this.languageIndex = languageIndex;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCustom_info_name() {
        return custom_info_name;
    }

    public void setCustom_info_name(String custom_info_name) {
        this.custom_info_name = custom_info_name;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "AuthLoginBean{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
