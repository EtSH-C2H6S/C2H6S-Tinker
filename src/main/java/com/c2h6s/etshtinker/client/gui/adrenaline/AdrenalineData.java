package com.c2h6s.etshtinker.client.gui.adrenaline;

public class AdrenalineData {
    public static int Adrenaline =0;
    public static boolean isHelding =false;

    public static void setAdrenaline(int amount,boolean helding){
        Adrenaline =amount;
        isHelding =helding;
    }

    public static int getAdrenaline(){
        return Adrenaline;
    }

    public static boolean gethleding(){
        return isHelding;
    }
}
