package org.owasp.esapi.spring.boot;
/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */

import java.util.ArrayList;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

/**
 * https://blog.csdn.net/frog4/article/details/81876462
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
public class EsapiTest {
	public static void main(String args[]) {
		System.out.println(ESAPI.encoder().encodeForHTML("<a href='sdfs'></a> < script > alert(); </ script >"));
		
		// 针对xss漏洞
		
		//对用户输入“input”进行HTML编码，防止XSS 
		String inputx = ESAPI.encoder().encodeForHTML(""); 
		//根据自己不同的需要可以选用以下方法 
		//input = ESAPI.encoder().encodeForHTMLAttribute(input); 
		//input = ESAPI.encoder().encodeForJavaScript(input); 
		//input = ESAPI.encoder().encodeForCSS(input); 
		//input = ESAPI.encoder().encodeForURL(input); 
		//针对富文本进行html编码
		
		//针对sql注入漏洞

		//除了支持mysql还支持oracle
		
		String input1="用户输入1"; 
		String input2="用户输入2"; 
		//解决注入问题 
		input1 = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD),input1); 
		input2 = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD),input2);
		String sqlStr="select name from tableA where id="+input1 +"and date_created ='" + input2+"'"; 
		//执行SQL
		System.out.println(sqlStr);
		
		
		// 验证输入
		/*
			检查每个输入的有效性，让每个输入合法。这里面的关键是一切都进行验证。ESAPI提供了很多常见的校验，可以方便针对不同的需要做校验。
		 */
		
		//type就是定义在validate.properties文件中的正则表达式
		//ESAPI.validator().getValidInput(java.lang.String context,java.lang.String input, java.lang.String type,int maxLength,boolean allowNull); 
		// ESAPI.validator().isValidInput(java.lang.String context,java.lang.String
		// input, java.lang.String type,int maxLength,boolean allowNull);
		// 实际使用参考如下：
		String input = "xxxx.com";
		if (!ESAPI.validator().isValidInput("", input, "Email", 11, false)) {
			System.out.println("出错了");
		}
		try {
			input = ESAPI.validator().getValidInput("", input, "Email", 11, false);
		} catch (Exception e) {
			System.out.println("输入错误");
			e.printStackTrace();
		}
		
		// 验证恶意文件

		// 校验文件名
		String inputfilename = "xxxx.txt";
		ArrayList<String> allowedExtension = new ArrayList<String>();
		allowedExtension.add("exe");
		allowedExtension.add("jpg");
		if (!ESAPI.validator().isValidFileName("upload", inputfilename, allowedExtension, false)) {
			// 文件名不合法, throw exception
			System.out.println("出错了");
		}
		// 获取随机文件名
		System.out.println(ESAPI.randomizer().getRandomFilename("exe"));
		// 得到结果rnQO8AK4ymmv.exe
	}
}
