package com.education.online.util;

/**
 * Created by Administrator on 2016/8/12.
 */
import android.content.Context;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public class XmlParse {
    private static final String TAG = "XmlParse";

    public XmlParse() {
    }

    public static String getPapers(Context context, String key_value, String parent_key) {
        try {
            InputStream e = context.getAssets().open("plist.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(e, "UTF-8");
            boolean tag = false;
            String dictName = "";

            for(String nameValue = ""; parser.getEventType() != 1; parser.next()) {
                if(parser.getEventType() == 2) {
                    String tagName = parser.getName();
                    if("dict".equals(tagName)) {
                        dictName = parser.getAttributeValue((String)null, "name");
                    }

                    if("string".equals(tagName)) {
                        nameValue = parser.getAttributeValue((String)null, "name");
                        tag = true;
                    }
                }

                if(parser.getEventType() == 4 && tag) {
                    if(key_value.equals(nameValue) && dictName.equals(parent_key)) {
                        key_value = parser.getText();
                        break;
                    }

                    tag = false;
                }
            }

            e.close();
        } catch (Exception var9) {
            LogUtil.e("XmlParse", "数字字典转换失败", var9);
        }

        return key_value;
    }

    public boolean writeToXml(Context context, String str) {
        try {
            FileOutputStream e = context.openFileOutput("users.xml", '耀');
            OutputStreamWriter outw = new OutputStreamWriter(e);

            try {
                outw.write(str);
                outw.close();
                e.close();
                return true;
            } catch (IOException var6) {
                return false;
            }
        } catch (FileNotFoundException var7) {
            return false;
        }
    }

    public String writeToString(String tag, String value) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("utf-8", Boolean.valueOf(true));
            serializer.startTag("", "dicts");
            serializer.startTag("", "dict");
            serializer.attribute("", "name", "cache");
            serializer.startTag("", "string");
            serializer.attribute("", "name", tag);
            serializer.text(value);
            serializer.endTag("", "string");
            serializer.endTag("", "dict");
            serializer.endTag("", "dicts");
            serializer.endDocument();
        } catch (IllegalArgumentException var6) {
            var6.printStackTrace();
        } catch (IllegalStateException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return writer.toString();
    }

    public static String getIntentLoginAction(Context context) {
        return getPapers(context, "intent_login", "constants");
    }

    public static String getIntentPayAction(Context context) {
        return getPapers(context, "intent_pay", "constants");
    }

    public static String getIntentHomeAction(Context context) {
        return getPapers(context, "intent_home", "constants");
    }

    public static String getDialogProgress(Context context) {
        return getPapers(context, "dialog_progress", "constants");
    }
}
