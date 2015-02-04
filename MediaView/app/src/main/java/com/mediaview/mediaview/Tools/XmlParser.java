package com.mediaview.mediaview.tools;

import android.content.Context;
import android.util.Xml;

import com.mediaview.mediaview.DAO.accessor.MediaDataAccessor;
import com.mediaview.mediaview.model.Media;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kazuya on 14/11/2014.
 * MediaView
 */
public class XmlParser {

    private static final String ns = null;
    private static final String ELEMENT = "media";
    private static final String ROOT_ELEMENT = "medias";
    private Context mContext;

    public XmlParser(Context mContext) {
        this.mContext = mContext;
    }

    public List<Media> parse(InputStream in){
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parseXML(parser);
//        return readFeed(parser);
    }

    private List<Media> parseXML(XmlPullParser parser) {

        List<Media> medias = null;
        MediaDataAccessor accessor = new MediaDataAccessor(mContext);
        try{
            int eventType = parser.getEventType();
            Media currentMedia = null;
            int id = 0;

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:

                        medias = new ArrayList();
                        break;

                    case XmlPullParser.START_TAG:

                        name = parser.getName();

                        if (name.equalsIgnoreCase(ELEMENT)){

                            int version = Integer.parseInt(parser.getAttributeValue(null, "versionCode"));
                            String nameMedia = parser.getAttributeValue(null, "name");
                            String type = parser.getAttributeValue(null, "type");
                            String path = parser.getAttributeValue(null, "path");

                            if(accessor.isNewMedia(version, nameMedia, type, path)) {
                                currentMedia = new Media();

                                currentMedia.setId(++id);
                                currentMedia.setName(nameMedia);
                                currentMedia.setType(Manager.getInstance().getEnumFromString(type));
                                currentMedia.setUrl(path);
                                currentMedia.setVersion(version);
                                currentMedia.setLocal(false);

                                medias.add(currentMedia);
                            }

                        }
                        break;

                    case XmlPullParser.END_TAG:

                        name = parser.getName();
                        break;
                }
                eventType = parser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return medias;

    }

}
