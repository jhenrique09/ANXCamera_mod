package com.ss.android.ugc.effectmanager.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EffectConstants {
    public static final String CHANNEL_LOCAL_TEST = "local_test";
    public static final String CHANNEL_ONLINE = "online";
    public static final String CHANNEL_TEST = "test";
    public static final String COMPRESSED_FILE_SUFFIX = ".zip";
    public static final String DELETE = "DELETE";
    public static final int EFFECT_CANCEL = 22;
    public static final int EFFECT_CHANNEL_CANCEL = 25;
    public static final int EFFECT_CHANNEL_DOWNLOAD = 23;
    public static final int EFFECT_CHANNEL_DOWNLOADING = 24;
    public static final int EFFECT_CHANNEL_DOWNLOAD_FAILED = 27;
    public static final int EFFECT_DOWNLOADED = 20;
    public static final int EFFECT_DOWNLOADING = 21;
    public static final int EFFECT_DOWNLOAD_FAILED = 26;
    public static final int EFFECT_TYPE_COLLECT = 1;
    public static final int EFFECT_TYPE_NORMAL = 0;
    public static final int EFFECT_TYPE_SCHEME = 2;
    public static final int FINAL_SPEED_MEASURE = 31;
    public static final String GET = "GET";
    public static final String GIF_FILE_SUFFIX = ".gif";
    public static final String KEY_CATEGORY_VERSION = "category_version";
    public static final String KEY_EFFECT_CHANNEL = "effectchannel";
    public static final String KEY_EFFECT_UPDATE_TIME = "updatetime";
    public static final String KEY_EFFECT_VERSION = "effect_version";
    public static final String LINK_SELECTOR = "LINK_SELECTOR";
    public static final int MSG_CATEGORY_EFFECT_LIST = 21;
    public static final int MSG_CHECK_UPDATE = 13;
    public static final int MSG_DOWNLOAD_EFFECT = 15;
    public static final int MSG_DOWNLOAD_EFFECT_LIST = 17;
    public static final int MSG_DOWNLOAD_PROVIDER_EFFECT = 19;
    public static final int MSG_DOWNLOAD_PROVIDER_EFFECT_LIST = 20;
    public static final int MSG_EFFECT_LIST = 14;
    public static final int MSG_EFFECT_START = 42;
    public static final int MSG_EXIST_EFFECT = 16;
    public static final int MSG_FAVORITE = 40;
    public static final int MSG_FAVORITE_LIST = 41;
    public static final int MSG_PANEL_INFO = 22;
    public static final int MSG_PROVIDER_EFFECT_LIST = 18;
    public static final int MSG_READ_TAG = 52;
    public static final int MSG_SIGNGLE_URL_SPEED_MEASURE = 30;
    public static final int MSG_WRITE_TAG = 51;
    public static final String NETWORK = "NETWORK";
    public static final String NORMAL = "NORMAL";
    public static final String PATCH = "PATCH";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String ROUTE_CATEGORY_CHECK_UPDATE = "/category/check";
    public static final String ROUTE_CATEGORY_EFFECT = "/category/effects";
    public static final String ROUTE_CHECK_UPDATE = "/checkUpdate";
    public static final String ROUTE_CHECK_UPDATE_PAGE = "/panel/check";
    public static final String ROUTE_EFFECT_LIST = "/v3/effects";
    public static final String ROUTE_FAVORITE = "/v3/effect/favorite";
    public static final String ROUTE_FAVORITE_LIST = "/v3/effect/my";
    public static final String ROUTE_FILTER_LIST = "/filters";
    public static final String ROUTE_LIST = "/v3/effect/list";
    public static final String ROUTE_PANEL_INFO = "/panel/info";
    public static final String ROUTE_PROVIDER_LIST = "/stickers/recommend";
    public static final String ROUTE_SEARCH_PROVIDER_LIST = "/stickers/search";

    @Retention(RetentionPolicy.SOURCE)
    public @interface HTTP_METHOD {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface INTERCEPTION_NAME {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TASK_TYPE {
    }
}
