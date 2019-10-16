package com.ruimin.ifs.batch.bean.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
/**
 * @deprecated:[框架自带-批量功能]批量任务表
 * 创建日期:2019-01-17 13:13:19
 */
@Table("IFS_BAT_JOB_INF")
public class IfsBatJobInf{

   /**
      *任务编号 
      *数据类型：int(11) 
      *是否必填:true 
   **/
   @Id
   private java.lang.Integer jobno;
   /**
      *前置任务编号 
      *数据类型：int(11) 
      *是否必填:false 
   **/
   private java.lang.Integer preJobno;
   /**
      *前置任务是否自动运行1=自动0=不自动 
      *数据类型：char(1) 
      *是否必填:false 
   **/
   private java.lang.String preAutorun;
   /**
      *系统类型,与IFS_SYS_INF表中类型SYSTEM_TYPE保持一致，默认1 
      *数据类型：char(2) 
      *是否必填:false 
   **/
   private java.lang.String systemType;
   /**
      *作业是否允许在联机状态下运行0=不允许1=允许2=忽略联机状态 
      *数据类型：char(2) 
      *是否必填:false 
   **/
   private java.lang.String runonline;
   /**
      *创建时间 
      *数据类型：char(17) 
      *是否必填:false 
   **/
   private java.lang.String timestamps;
   /**
      *扩展字段 
      *数据类型：varchar(256) 
      *是否必填:false 
   **/
   private java.lang.String miscflgs;
   /**
      *扩展字段(任务描述) 
      *数据类型：varchar(256) 
      *是否必填:false 
   **/
   private java.lang.String misc;

  //get和set方法
   /**
    *任务编号
    *@return java.lang.Integer
    */
   public java.lang.Integer getJobno(){
      return jobno;
   }

   /**
    *任务编号
    *@param jobno
    */
   public void setJobno(java.lang.Integer jobno){
      this.jobno = jobno;
   }

   /**
    *前置任务编号
    *@return java.lang.Integer
    */
   public java.lang.Integer getPreJobno(){
      return preJobno;
   }

   /**
    *前置任务编号
    *@param preJobno
    */
   public void setPreJobno(java.lang.Integer preJobno){
      this.preJobno = preJobno;
   }

   /**
    *前置任务是否自动运行1=自动0=不自动
    *@return java.lang.String
    */
   public java.lang.String getPreAutorun(){
      return preAutorun;
   }

   /**
    *前置任务是否自动运行1=自动0=不自动
    *@param preAutorun
    */
   public void setPreAutorun(java.lang.String preAutorun){
      this.preAutorun = preAutorun;
   }

   /**
    *系统类型,与IFS_SYS_INF表中类型SYSTEM_TYPE保持一致，默认1
    *@return java.lang.String
    */
   public java.lang.String getSystemType(){
      return systemType;
   }

   /**
    *系统类型,与IFS_SYS_INF表中类型SYSTEM_TYPE保持一致，默认1
    *@param systemType
    */
   public void setSystemType(java.lang.String systemType){
      this.systemType = systemType;
   }

   /**
    *作业是否允许在联机状态下运行0=不允许1=允许2=忽略联机状态
    *@return java.lang.String
    */
   public java.lang.String getRunonline(){
      return runonline;
   }

   /**
    *作业是否允许在联机状态下运行0=不允许1=允许2=忽略联机状态
    *@param runonline
    */
   public void setRunonline(java.lang.String runonline){
      this.runonline = runonline;
   }

   /**
    *创建时间
    *@return java.lang.String
    */
   public java.lang.String getTimestamps(){
      return timestamps;
   }

   /**
    *创建时间
    *@param timestamps
    */
   public void setTimestamps(java.lang.String timestamps){
      this.timestamps = timestamps;
   }

   /**
    *扩展字段
    *@return java.lang.String
    */
   public java.lang.String getMiscflgs(){
      return miscflgs;
   }

   /**
    *扩展字段
    *@param miscflgs
    */
   public void setMiscflgs(java.lang.String miscflgs){
      this.miscflgs = miscflgs;
   }

   /**
    *扩展字段(任务描述)
    *@return java.lang.String
    */
   public java.lang.String getMisc(){
      return misc;
   }

   /**
    *扩展字段(任务描述)
    *@param misc
    */
   public void setMisc(java.lang.String misc){
      this.misc = misc;
   }

}