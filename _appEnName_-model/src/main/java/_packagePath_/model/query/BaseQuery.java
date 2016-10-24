package ${packageName}.model.query;


/**
 * Created by shengshan.tang on 2015/11/27 at 14:12
 */
public class BaseQuery extends com.yuntao.platform.common.model.BaseQuery {

    private Long id;

    private Long userId;

    private String groupBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
