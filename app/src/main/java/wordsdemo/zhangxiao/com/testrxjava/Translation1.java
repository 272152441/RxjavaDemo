package wordsdemo.zhangxiao.com.testrxjava;

import android.util.Log;

/**
 * @author ZhangXiao
 * @date 2018/2/7
 */

public class Translation1 {

    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","out":"示例","vendor":"ciba","err_no":0}
     */

    private int status;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * from : en-EU
         * to : zh-CN
         * out : 示例
         * vendor : ciba
         * err_no : 0
         */

        private String from;
        private String to;
        private String out;
        private String vendor;
        private int err_no;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }


    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("翻译内容", content.out );
    }
}
