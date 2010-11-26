/*
* Copyright 2010 The Apache Software Foundation
*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.bizosys.hsearch.index;

import junit.framework.TestCase;

import com.bizosys.ferrari.TestFerrari;

public class TermTest extends TestCase {

	public static void main(String[] args) throws Exception {
		TermTest t = new TermTest();
        TestFerrari.testAll(t);
	}
	
	public void testSerialize() throws Exception {
		Term t = new Term();
		assertEquals(0 , t.getTermPos(t.setTermPos(0)) );
		assertEquals(23 , t.getTermPos(t.setTermPos(23)) );
		assertEquals(64998, t.getTermPos(t.setTermPos(64998)) );
		assertEquals(64999, t.getTermPos(t.setTermPos(64999)) );
		assertEquals(65000, t.getTermPos(t.setTermPos(65000)) );
		assertEquals(65001, t.getTermPos(t.setTermPos(65001)) );
		assertEquals(65002, t.getTermPos(t.setTermPos(65002)) );
		assertEquals(42234, t.getTermPos(t.setTermPos(42234)) );
		assertEquals(-1, t.getTermPos(t.setTermPos(-1)) );
		assertEquals(36435345, t.getTermPos(t.setTermPos(36435345)) );
		assertEquals(482324, t.getTermPos(t.setTermPos(482324)) );
		assertEquals(7823435, t.getTermPos(t.setTermPos(7823435)) );
		assertEquals(-2134324 ,t.getTermPos(t.setTermPos(-2134324)) );
		
		Term term = new Term();
		term.setTermFrequency((byte)122) ;
		
		Term term2 = new Term();
		term2.setTermFrequency((byte) 2);
		term.merge(term2);

		System.out.println("Term Frequency :" + term.getTermFrequency());
	}
}
