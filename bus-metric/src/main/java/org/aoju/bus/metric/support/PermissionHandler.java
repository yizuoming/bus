/*
 * The MIT License
 *
 * Copyright (c) 2020 aoju.org All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.aoju.bus.metric.support;

import org.aoju.bus.core.lang.exception.AuthorizedException;
import org.aoju.bus.logger.Logger;
import org.aoju.bus.metric.Context;
import org.aoju.bus.metric.builtin.Errors;
import org.aoju.bus.metric.handler.ApiHandlerAdapter;
import org.aoju.bus.metric.magic.ApiParam;
import org.aoju.bus.metric.oauth2.PermissionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限验证拦截器
 *
 * @author Kimi Liu
 * @version 5.5.2
 * @since JDK 1.8++
 */
public class PermissionHandler extends ApiHandlerAdapter {

    private AuthorizedException exception = new AuthorizedException(Errors.NO_PERMISSION.getMsg(), Errors.NO_PERMISSION.getCode());


    public PermissionHandler() {
    }

    /**
     * @param code 禁止访问错误码
     * @param msg  禁止访问信息
     */
    public PermissionHandler(String code, String msg) {
        this.exception = new AuthorizedException(msg, code);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu) {
        PermissionManager permissionManager = Context.getConfig().getPermissionManager();
        if (permissionManager == null) {
            Logger.warn("权限PermissionManager为null，请检查设置！");
            return true;
        }
        ApiParam param = Context.getApiParam();
        boolean canVisit = permissionManager.canVisit(param.fatchAppKey(), param.fatchName(), param.fatchVersion());
        if (!canVisit) {
            throw exception;
        }
        return canVisit;
    }

}
