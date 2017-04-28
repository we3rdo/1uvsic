package com.kgv.cookbook.config;

import android.content.Context;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.kgv.cookbook.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2017/2/28 9:36
 * Email : 18627241899@163.com
 * Description :
 */
public class VoiceHelper {

    private static VoiceHelper helper;
    private SpeechRecognizer speechRecognizer;
    private RecognizerListener listener;
    private RecognizerDialog dialog;

    public void init(Context context,RecognizerListener listener,RecognizerDialogListener dialogListener){
        speechRecognizer = SpeechRecognizer.createRecognizer(context, null);
        dialog = new RecognizerDialog(context,null);
        this.listener = listener;
        dialog.setParameter(SpeechConstant.DOMAIN, "iat");
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ASR_PTT, "0");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(dialogListener);
        speechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        speechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
    }

    public void startRecording() {
        speechRecognizer.startListening(listener);

    }

    public void start4DiaLog(){
        dialog.show();
    }

    public void setCantonese() {
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "cantonese");
        dialog.setParameter(SpeechConstant.ACCENT, "cantonese");
    }

    public void setMandarin() {
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
    }

    public void destroy() {
        speechRecognizer.destroy();
        dialog.destroy();
    }

    public String parseResult(String json) {
        StringBuilder ret = new StringBuilder();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    public ArrayList<String> parseResult4List(String json) {
        ArrayList<String> result = new ArrayList<>();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject obj = items.getJSONObject(j);
                    result.add(obj.getString("w"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static VoiceHelper getInstance() {
        if (helper == null) {
            synchronized (HttpUtils.class) {
                if (helper == null) {
                    helper = new VoiceHelper();
                }
            }
        }
        return helper;
    }
}
