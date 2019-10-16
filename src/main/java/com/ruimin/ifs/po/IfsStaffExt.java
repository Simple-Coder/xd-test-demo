package com.ruimin.ifs.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
/**
 * @deprecated:用户扩展信息
 * 创建日期:2016-10-14 11:24:53
 */
@Table("IFS_STAFF_EXT")
public class IfsStaffExt{

   @Id
   private String tlrno;
   private String telNo;
   private String email;
   private String qqNo;
   private String wxNo;
   private String headImg;

  //get和set方法
   public String getTlrno(){
      return tlrno;
   }

   public void setTlrno(String tlrno){
      this.tlrno = tlrno;
   }

   public String getTelNo(){
      return telNo;
   }

   public void setTelNo(String telNo){
      this.telNo = telNo;
   }

   public String getEmail(){
      return email;
   }

   public void setEmail(String email){
      this.email = email;
   }

   public String getQqNo(){
      return qqNo;
   }

   public void setQqNo(String qqNo){
      this.qqNo = qqNo;
   }

   public String getWxNo(){
      return wxNo;
   }

   public void setWxNo(String wxNo){
      this.wxNo = wxNo;
   }

   public String getHeadImg(){
      return headImg;
   }

   public void setHeadImg(String headImg){
      this.headImg = headImg;
   }

}