/*
 * 
 * 
 * 
 * 
 */

package ${packageName}.model.domain;
import com.yuntao.platform.common.annotation.ModelFieldComment;

import java.io.Serializable;
import java.util.Date;

/**
 * ${entityCnName}
 * @author ${author}
 *
 * @${time}
 */
public class ${upperEntityEnName} implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    #foreach($item in $!bo.propList)
@ModelFieldComment(value = "$!{item.cnName}")
    private $!{item.dataType} $!{item.enName};
        
    #end

    public ${upperEntityEnName}(){
    }

    #foreach($item in $!bo.propList)
public void set$!{item.upperEnName}($!{item.dataType} value) {
        this.$!{item.enName} = value;
    }
    
    public $!{item.dataType} get$!{item.upperEnName}() {
        return this.$!{item.enName};
    }
    #end




}