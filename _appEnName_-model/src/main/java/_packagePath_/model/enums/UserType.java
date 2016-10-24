package ${packageName}.model.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuntao.platform.common.annotation.EnumComment;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@EnumComment("用户类型")
public enum UserType {

    member(1, "会员"),

    biz(2, "商户"),

    admin(3, "管理员"),

    operate(4, "运营"),

    ALL(0, "所有"),;


    private int code;
    private String description;

    UserType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserType getByCode(int code) {
        for (UserType s : UserType.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
