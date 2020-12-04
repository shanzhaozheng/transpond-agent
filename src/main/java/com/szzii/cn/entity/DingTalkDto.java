package com.szzii.cn.entity;

import java.io.Serializable;
import java.util.List;

public class DingTalkDto implements Serializable {

    /**
     * touser :
     * toparty :
     * agentid :
     * msgtype :
     * text : {"content":""}
     */

    private String touser;
    private String toparty;
    private String agentid;
    private String msgtype;
    private TextDTO text;
    /**
     * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
     */
    private AtDTO at;


    public static class TextDTO {
        /**
         * content :
         */

        private String content;

        public TextDTO() {
        }

        public TextDTO(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextDTO getText() {
        return text;
    }

    public AtDTO getAt() {
        return at;
    }

    public void setAt(AtDTO at) {
        this.at = at;
    }

    public void setText(TextDTO text) {
        this.text = text;
    }

    public static class AtDTO {

        public AtDTO() {
        }

        public AtDTO(Boolean isAtAll, List<String> atMobiles) {
            this.isAtAll = isAtAll;
            this.atMobiles = atMobiles;
        }

        /**
         * atMobiles : ["156xxxx8827","189xxxx8325"]
         * isAtAll : false
         */



        private Boolean isAtAll;
        private List<String> atMobiles;

        public Boolean getAtAll() {
            return isAtAll;
        }

        public void setAtAll(Boolean atAll) {
            isAtAll = atAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }
    }
}
