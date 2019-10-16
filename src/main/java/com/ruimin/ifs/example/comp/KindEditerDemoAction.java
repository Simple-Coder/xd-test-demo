package com.ruimin.ifs.example.comp;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.encrypt.Base64Encrypt;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.example.bean.KindBean;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
import com.ruimin.ifs.framework.dataobject.UpdateRequestBean;

@ActionResource
@SnowDoc(desc = "kindEditer")
public class KindEditerDemoAction extends SnowAction {

    @Action
    public KindBean getKindBean(QueryParamBean queryParamBean) throws SnowException {
        KindBean bean = new KindBean();
        // text
        String html = "这个一段文本";
        bean.setF1(html);
        // 未编码html
        String html2 = "<h1>这个是一段html</h1><br/><hr><h2 style='color:red'>KindEditer内容经过编码显示</h2>";
        bean.setF2(Base64Encrypt.getInstance().encrypt(html2));
        // 编码html
        String html3 =
                "<h1>这个是一段html</h1><br/><hr><h2 style='color:green'>KindEditer直接展示后台html</h2>";
        bean.setF3(html3);
        return bean;
    }

    /**
     * 提交处理
     * 
     * @Title submitKindBean
     * @Description TODO
     * @param updateMap
     * @throws SnowException
     */
    @Action
    public void submitKindBean(Map<String, UpdateRequestBean> updateMap) throws SnowException {
        UpdateRequestBean kindBeanRequest = updateMap.get("KindBean");
        Base64Encrypt instance = Base64Encrypt.getInstance();
        if (kindBeanRequest.hasNext()) {
            Map<String, String> dataMap = kindBeanRequest.next();
            for (Entry<String, String> entry : dataMap.entrySet()) {
                if (StringUtils.equals(entry.getKey(), "f1")
                        || StringUtils.equals(entry.getKey(), "recordState")) {
                    System.out.println(entry.getKey() + "===" + entry.getValue());
                } else {
                    if (StringUtils.isNotBlank(entry.getValue())) {
                        System.out.println(
                                entry.getKey() + "===" + instance.decrypt(entry.getValue()));
                    } else {
                        System.out.println(entry.getKey() + "===" + entry.getValue());
                    }
                }
            }
        }
    }


}
