package com.dd.rbook.config

/**
 * @Description 常量管理类
 */
interface Constants {

    object Key {
        const val AGREE_PRIVACYPOLICY = "agreePrivacyPolicy"
        const val SEARCH_FILTE = "search_filter" //0全部查找，1.模糊查找2.精确查找
    }

    /**
     * value规则： /(module后缀)/(所在类名)
     * 路由 A_ : Activity
     *     F_ : Fragment
     */
    interface Router {

        object Main {
            const val A_MAIN = "/main/MainActivity"
            const val A_SPLASH = "/main/SplashActivity"
            const val A_SEARCH = "/main/SearchActivity"
            const val A_SEARCHRESULT = "/main/SearchResultActivity"

        }
    }
}