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
package org.aoju.bus.metric.builtin.doc;

import org.aoju.bus.metric.annotation.Api;
import org.aoju.bus.metric.annotation.Service;
import org.springframework.context.ApplicationContext;

/**
 * @author Kimi Liu
 * @version 5.5.2
 * @since JDK 1.8++
 */
public class ApiServiceDocCreator extends AbstractApiDocCreator<Service, Api> {

    public ApiServiceDocCreator(String defaultVersion, ApplicationContext applicationContext) {
        super(defaultVersion, applicationContext);
    }

    @Override
    protected Class<Service> getServiceAnnotationClass() {
        return Service.class;
    }

    @Override
    protected Class<Api> getMethodAnnotationClass() {
        return Api.class;
    }

    @Override
    protected org.aoju.bus.metric.magic.Api getApi(Api api) {
        return new org.aoju.bus.metric.magic.Api(api.name(), api.version());
    }
}
