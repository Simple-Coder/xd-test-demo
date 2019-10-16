package com.ruimin.ifs.batch.bean.po;

import com.ruimin.ifs.rql.annotation.Id;
import com.ruimin.ifs.rql.annotation.Table;
/**
 * @deprecated:[框架自带-批量功能]批量运行日志表
 * 创建日期:2019-01-21 14:38:55
 */
@Table("IFS_BAT_PROC_LOG")
public class IfsBatProcLog{

   /**
      *主键 
      *数据类型：int(11) 
      *是否必填:true 
   **/
   @Id
   private java.lang.Integer id;
   /**
      *批量日期 
      *数据类型：char(8) 
      *是否必填:true 
   **/
   private java.lang.String bhdate;
   /**
      *任务编号 
      *数据类型：int(11) 
      *是否必填:true 
   **/
   private java.lang.Integer jobno;
   /**
      *步骤编号 
      *数据类型：int(11) 
      *是否必填:true 
   **/
   private java.lang.Integer step;
   /**
      *子步骤编号 
      *数据类型：int(11) 
      *是否必填:true 
   **/
   private java.lang.Integer subStep;
   /**
      *批量运行类名称_JOBNO_STEP_SUB_STEP 
      *数据类型：varchar(120) 
      *是否必填:false 
   **/
   private java.lang.String processFunction;
   /**
      *默认0 
      *数据类型：int(11) 
      *是否必填:false 
   **/
   private java.lang.Integer processid;
   /**
      *开始时间 
      *数据类型：char(17) 
      *是否必填:false 
   **/
   private java.lang.String startTime;
   /**
      *结束时间 
      *数据类型：char(17) 
      *是否必填:false 
   **/
   private java.lang.String endTime;
   /**
      *运行状态0=未运行R=运行中E=已出错F=已完成 
      *数据类型：char(1) 
      *是否必填:false 
   **/
   private java.lang.String status;
   /**
      *最后更新时间 
      *数据类型：char(17) 
      *是否必填:false 
   **/
   private java.lang.String timestamps;
   /**
      *错误描述 
      *数据类型：varchar(1024) 
      *是否必填:false 
   **/
   private java.lang.String errMsg;
   /**
      *异常信息 
      *数据类型：varchar(1024) 
      *是否必填:false 
   **/
   private java.lang.String desc1;
   /**
      *预留字段 
      *数据类型：varchar(1024) 
      *是否必填:false 
   **/
   private java.lang.String desc2;

  //get和set方法
   /**
    *主键
    *@return java.lang.Integer
    */
   public java.lang.Integer getId(){
      return id;
   }

   /**
    *主键
    *@param id
    */
   public void setId(java.lang.Integer id){
      this.id = id;
   }

   /**
    *批量日期
    *@return java.lang.String
    */
   public java.lang.String getBhdate(){
      return bhdate;
   }

   /**
    *批量日期
    *@param bhdate
    */
   public void setBhdate(java.lang.String bhdate){
      this.bhdate = bhdate;
   }

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
    *步骤编号
    *@return java.lang.Integer
    */
   public java.lang.Integer getStep(){
      return step;
   }

   /**
    *步骤编号
    *@param step
    */
   public void setStep(java.lang.Integer step){
      this.step = step;
   }

   /**
    *子步骤编号
    *@return java.lang.Integer
    */
   public java.lang.Integer getSubStep(){
      return subStep;
   }

   /**
    *子步骤编号
    *@param subStep
    */
   public void setSubStep(java.lang.Integer subStep){
      this.subStep = subStep;
   }

   /**
    *批量运行类名称_JOBNO_STEP_SUB_STEP
    *@return java.lang.String
    */
   public java.lang.String getProcessFunction(){
      return processFunction;
   }

   /**
    *批量运行类名称_JOBNO_STEP_SUB_STEP
    *@param processFunction
    */
   public void setProcessFunction(java.lang.String processFunction){
      this.processFunction = processFunction;
   }

   /**
    *默认0
    *@return java.lang.Integer
    */
   public java.lang.Integer getProcessid(){
      return processid;
   }

   /**
    *默认0
    *@param processid
    */
   public void setProcessid(java.lang.Integer processid){
      this.processid = processid;
   }

   /**
    *开始时间
    *@return java.lang.String
    */
   public java.lang.String getStartTime(){
      return startTime;
   }

   /**
    *开始时间
    *@param startTime
    */
   public void setStartTime(java.lang.String startTime){
      this.startTime = startTime;
   }

   /**
    *结束时间
    *@return java.lang.String
    */
   public java.lang.String getEndTime(){
      return endTime;
   }

   /**
    *结束时间
    *@param endTime
    */
   public void setEndTime(java.lang.String endTime){
      this.endTime = endTime;
   }

   /**
    *运行状态0=未运行R=运行中E=已出错F=已完成
    *@return java.lang.String
    */
   public java.lang.String getStatus(){
      return status;
   }

   /**
    *运行状态0=未运行R=运行中E=已出错F=已完成
    *@param status
    */
   public void setStatus(java.lang.String status){
      this.status = status;
   }

   /**
    *最后更新时间
    *@return java.lang.String
    */
   public java.lang.String getTimestamps(){
      return timestamps;
   }

   /**
    *最后更新时间
    *@param timestamps
    */
   public void setTimestamps(java.lang.String timestamps){
      this.timestamps = timestamps;
   }

   /**
    *错误描述
    *@return java.lang.String
    */
   public java.lang.String getErrMsg(){
      return errMsg;
   }

   /**
    *错误描述
    *@param errMsg
    */
   public void setErrMsg(java.lang.String errMsg){
      this.errMsg = errMsg;
   }

   /**
    *异常信息
    *@return java.lang.String
    */
   public java.lang.String getDesc1(){
      return desc1;
   }

   /**
    *异常信息
    *@param desc1
    */
   public void setDesc1(java.lang.String desc1){
      this.desc1 = desc1;
   }

   /**
    *预留字段
    *@return java.lang.String
    */
   public java.lang.String getDesc2(){
      return desc2;
   }

   /**
    *预留字段
    *@param desc2
    */
   public void setDesc2(java.lang.String desc2){
      this.desc2 = desc2;
   }

}