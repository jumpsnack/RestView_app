package kr.ac.kmu.ncs.restview;

import java.util.HashMap;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class MyConstatns {
    /**
     * XIVELY CONSTANTS
     */
    final static public short REQUEST_TIME_INTERVAL = 2000;

    /**
     * SEVICE CONSTANTS
     */
    final static public byte REGIST_CLIENT = 1;
    final static public byte UNREGIST_CLIENT = 2;

    /**
     * DATASTREAM CONSTANTS
     */
    static public String contents = "";
    //static public HashMap<String, String> parameters = new HashMap<String ,String>();
    //static public Vector<HashMap> parameterVector = new Vector<>();
    static public HashMap<String, HashMap> parentParameter = new HashMap<>();
    final static public byte SUCCESSFUL = 1;

    static public int cnt = 0;

    String[] DATA_KEYS = {"PIR", "IR", "BTN"};
}