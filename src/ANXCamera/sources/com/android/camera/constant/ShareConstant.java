package com.android.camera.constant;

import android.provider.MiuiSettings;
import com.android.camera.R;

public class ShareConstant {
    public static final String[] DEFAULT_SHARE_LIST = {"com.ss.android.ugc.aweme.share.SystemShareActivity", "com.ss.android.ugc.aweme.splash.SplashActivity", MiuiSettings.XSpace.KEY_WEIXIN_SEND_TO_FRIEND, MiuiSettings.XSpace.KEY_XSPACE_QQ_SEND_TO_FRIEND, "com.qzonex.module.maxvideo.activity.QzonePublishVideoActivity", MiuiSettings.XSpace.KEY_WEIBO_SEND};
    public static final int[] DEFAULT_SHARE_LIST_ICON = {R.drawable.ic_live_share_tik, R.drawable.ic_live_share_tik, R.drawable.ic_live_share_wechat, R.drawable.ic_live_share_qq, R.drawable.ic_live_share_qzone, R.drawable.ic_live_share_weibo};
    public static final int[] DEFAULT_SHARE_LIST_NAME = {R.string.live_share_tik, R.string.live_share_tik, R.string.live_share_wechat, R.string.live_share_qq, R.string.live_share_qzone, R.string.live_share_weibo};
}
