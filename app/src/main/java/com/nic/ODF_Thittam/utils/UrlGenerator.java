package com.nic.ODF_Thittam.utils;


import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.application.NICApplication;

/**
 * Created by Achanthi Sundar  on 21/03/16.
 */
public class UrlGenerator {



    public static String getLoginUrl() {
        return NICApplication.getAppString(R.string.LOGIN_URL);
    }

    public static String getServicesListUrl() {
        return NICApplication.getAppString(R.string.BASE_SERVICES_URL);
    }

    public static String getWorkListUrl() {
        return NICApplication.getAppString(R.string.APP_MAIN_SERVICES_URL);
//        return "http://10.163.19.140/rdweb/project/webservices_forms/work_monitoring/work_monitoring_services_test.php";
    }
    public static String getOpenUrl() {
        return NICApplication.getAppString(R.string.OPEN_SERVICES_URL);
    }
    public static String getRegistrationUrl() {
        return NICApplication.getAppString(R.string.REGISTRATION_SERVICES_URL);
    }


    public static String getTnrdHostName() {
        return NICApplication.getAppString(R.string.TNRD_HOST_NAME);
    }



}
