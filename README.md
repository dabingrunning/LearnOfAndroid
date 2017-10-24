# LearnOfAndroid
安卓学习


## 应用内启动微信（QQ）

* 首先知道微信（QQ）的包名以及启动页的类名

    >
     微信：com.tencent.mm   com.tencent.mm.ui.LauncherUi
    >
    QQ: com.tencent.mobileqq com.tencent.mobileqq.activity.HomeActivity

    ---
    检查本地是否安装微信
    ```
    public static boolean isWeixinAvilible(Context context) {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equals("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }
    ```
    启动微信
    ```java
    Intent intent = new Intent();
                        ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(cmp);
                        startActivity(intent);
    ```

