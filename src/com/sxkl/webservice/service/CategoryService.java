package com.sxkl.webservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sxkl.webservice.model.Category;

@Path("category")
public class CategoryService {
    
    @GET
    @Produces( {"application/json","application/xml"})
    /**
     * getCategories 产生json,xml两种数据格式，具体那种格式取决于contentType
     */
    public List<Category> getCategories() {
        List<Category> result =new ArrayList<Category>();
        result.add(new Category(1,"第一个分类"));
        result.add(new Category(2,"第二个分类"));
        return result;
    }

    @GET
    @Path("{id}")
    @Produces("applicatiion/json")
    public Category getCategory(@PathParam("id") int id) {
    	//TODO
    	//A message body writer for Java type, class com.sxkl.webservice.model.Category, and MIME media type, applicatiion/json, was not found
        return new Category(id,"id为"+id+"的类别");
    }

    
    @GET
    @Path("json/{id}")
    @Produces( "application/json")
    public JSONObject getCategoryJson(@PathParam("id") int id){//产生json
        JSONObject o = new JSONObject();
        try {
            o.put("id", id);
            o.put("name", "id为"+id+"的category");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
    
    @PUT
    @Path("add")
    @Produces("text/html")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public String addCategory(Category category) {
        System.out.println("处理添加类别逻辑，接受的数据为id:"+category.getId()+",name:"+category.getName());
        return "ok";
    }
    
    @POST
    @Path("addbyname")
    public String addCategory(@FormParam("categoryname") @DefaultValue("[未命名]") String cateogryname) {
        System.out.println("处理添加类别逻辑，接受的数据为name:"+cateogryname);
        return "添加类别"+cateogryname+"成功";
    }

    @POST
    @Produces("text/html")
    @Path("updatecategory")
    @Consumes( { "application/xml", "application/json" })
    public String updateCategory(Category category) {
        System.out.println("处理更新类别逻辑，接受的数据为id:"+category.getId()+",name:"+category.getName());
        return "ok";
    }

    @DELETE
    @Path("delete/{id}")
    public String deleteCategory(@PathParam("id") int id) {
        System.out.println("处理删除类别逻辑，接受的数据为id:"+id);
        return "ok";
    }
    
    @GET
    @Path("commonProcess")
    public String commonProcess(@Context UriInfo info){//@Context 参数标识UriInfo
         StringBuilder buf = new StringBuilder();  
         for (String param: info.getQueryParameters().keySet()) {  
             buf.append(param+" : "+info.getQueryParameters().get(param));  
             buf.append("\n");  
         }  
         System.out.println(buf.toString());
        return "ok";
    }
    
    
}