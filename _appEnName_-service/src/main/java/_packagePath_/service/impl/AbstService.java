package ${packageName}.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shengshan.tang on 2015/12/14 at 20:05
 */
public abstract class AbstService {

    protected final static Logger stackLog = LoggerFactory.getLogger("stackLog");

    protected final static Logger taskLog = LoggerFactory.getLogger("taskLog");

    protected final static Logger log = org.slf4j.LoggerFactory.getLogger(AbstService.class);
}
