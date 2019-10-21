/*
 * The MIT License
 *
 * Copyright (c) 2017 aoju.org All rights reserved.
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
package org.aoju.bus.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kimi Liu
 * @version 5.0.6
 * @since JDK 1.8+
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context {

    /**
     * RUL前缀
     */
    protected String prefix;
    /**
     * 容器名称
     */
    protected String bucket;
    /**
     * 服务端点
     */
    protected String endpoint;
    /**
     * 访问key
     **/
    protected String accessKey;
    /**
     * 访问秘钥
     **/
    protected String secretKey;
    /**
     * 存储区域
     */
    protected String region;
    /**
     * 是否私有
     */
    protected boolean secure;
    /**
     * 连接超时
     */
    protected long connectTimeout;
    /**
     * 写入超时
     */
    protected long writeTimeout;
    /**
     * 读取超时
     */
    protected long readTimeout;

}
