package com.example.accounting_app;

public class arrayListDef {
    private long id;
    private String date;
    private String inOrOut;
    private String tag;
    private double money;
    private String ps;

    //set parameters
    public void setId(long id){
        this.id=id;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setInOrOut(String inOrOut){
        this.inOrOut=inOrOut;
    }
    public void setTag(String tag){
        this.tag=tag;
    }
    public void setMoney(double money){
        this.money=money;
    }
    public void setPs(String ps){
        this.ps=ps;
    }
    //get parameters
    public long getId(long id){
        return this.id;
    }
    public String getDate(String date){
        return this.date;
    }
    public String getInOrOut(String inOrOut){
        return this.inOrOut;
    }
    public String getTag(String tag){
        return this.tag;
    }
    public double getMoney(double money){
        return this.money;
    }
    public String getPs(String ps){
        return this.ps;
    }

    // constructor
    public arrayListDef(long id,String date,String inOrOut,String tag,double money,String ps){
        this.id=id;
        this.date=date;
        this.inOrOut=inOrOut;
        this.tag=tag;
        this.money=money;
        this.ps=ps;
    }
}
