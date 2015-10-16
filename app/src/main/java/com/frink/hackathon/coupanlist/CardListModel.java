package com.frink.hackathon.coupanlist;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by shashwatsinha on 16/10/15.
 */
public class CardListModel implements Serializable {

    String name;
    String image_url;


    @Override
    public String toString() {
        String str = "";
        str = " " + name + " " + image_url + "\n";
        return str;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getName() {
        return name;
    }

    public CardListModel(String name ,String image_url){
        this.name = name;
        this.image_url = image_url;
    }

    /**
     * Created by shashwatsinha on 16/10/15.
     */
    public static class CardListModelList implements Serializable {
        public LinkedList<CardListModel> cards;

        @Override
        public String toString() {
            String str = "";
            Iterator<CardListModel> ie = cards.iterator();
            while (ie.hasNext()) {
                CardListModel cl = ie.next();
                str += cl.toString();
            }
            return str;
        }

        public LinkedList<CardListModel> getCards() {
            return cards;
        }
    }
}
