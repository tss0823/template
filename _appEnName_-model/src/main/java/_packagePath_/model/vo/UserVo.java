package ${packageName}.model.vo;

import ${packageName}.model.domain.User;

/**
 * Created by shengshan.tang on 2015/12/26 at 22:13
 */
public class UserVo extends User {

    private String statusText;

    private String levelText;

    private String courseStatusText;

    private String sexText;

    private String bindStatusText;

    private String submitInfoStatusText;

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getLevelText() {
        return levelText;
    }

    public void setLevelText(String levelText) {
        this.levelText = levelText;
    }

    public String getCourseStatusText() {
        return courseStatusText;
    }

    public void setCourseStatusText(String courseStatusText) {
        this.courseStatusText = courseStatusText;
    }

    public String getSexText() {
        return sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public String getBindStatusText() {
        return bindStatusText;
    }

    public void setBindStatusText(String bindStatusText) {
        this.bindStatusText = bindStatusText;
    }

    public String getSubmitInfoStatusText() {
        return submitInfoStatusText;
    }

    public void setSubmitInfoStatusText(String submitInfoStatusText) {
        this.submitInfoStatusText = submitInfoStatusText;
    }
}
