package com.rolmex.android.nemalltone.model;

public class ItemBean {
    private final static String TABLE_NAME = "item_table";
    
    private final static String ITEM_NAME = "item_name";
    
    private final static String ITEM_IMAGE = "item_image";
    
    private final static String ITEM_CLASS = "item_class";
    
    private final static String ITEM_COUNT = "item_count";
    
    public String item_name;
    
    public int item_image;
    
    public String item_class;
    
    public ItemBean(String name,int image,String className){
        this.item_name = name;
        this.item_image = image;
        this.item_class = className;
    }
    public ItemBean(){
        
    }
    

}
