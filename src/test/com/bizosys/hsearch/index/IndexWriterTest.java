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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.bizosys.ferrari.TestFerrari;
import com.bizosys.hsearch.common.HDocument;
import com.bizosys.hsearch.common.HField;
import com.bizosys.hsearch.dictionary.DictionaryManager;
import com.bizosys.hsearch.query.DocTeaserWeight;
import com.bizosys.hsearch.query.QueryContext;
import com.bizosys.hsearch.query.QueryResult;
import com.bizosys.hsearch.util.FileReaderUtil;
import com.bizosys.oneline.ApplicationFault;
import com.bizosys.oneline.conf.Configuration;
import com.bizosys.oneline.services.ServiceFactory;

public class IndexWriterTest extends TestCase {

	public static void main(String[] args) throws Exception {
		IndexWriterTest t = new IndexWriterTest();
		Configuration conf = new Configuration();
		ServiceFactory.getInstance().init(conf, null);
		List<String> kwL = DictionaryManager.getInstance().getDictionary().getAll();
		for (String kw : kwL) {
			System.out.println(kw.toString());
		}
		
		DictionaryManager.getInstance().deleteAll();
        TestFerrari.testRandom(t);
	}
	
	public void testIndexSingleDoc(String id, String name, String location) throws Exception {
		HDocument hdoc = new HDocument();
		hdoc.originalId = id;
		hdoc.title = name;
		hdoc.fields = new ArrayList<HField>();
		hdoc.fields.add(new HField("LOCATION", location));
		IndexWriter.getInstance().insert(hdoc);
		
		QueryResult res = IndexReader.getInstance().search(new QueryContext(name));
		assertEquals(1, res.teasers.length);
		DocTeaserWeight dtw = (DocTeaserWeight)res.teasers[0];
		assertEquals(id.toLowerCase(), new String(dtw.id.toBytes()));
		assertEquals(name, dtw.title.toString());
		System.out.println(res.toString());
	}
	
	public void testIndexSingleDocTwice(String id, String name, String location) throws Exception {
		this.testIndexSingleDoc(id, name, location);
		this.testIndexSingleDoc(id, name, location);
	}
	
	
	public void testIndexMultiDoc(String id1,String id2,String name, String location) throws Exception {
		HDocument hdoc = new HDocument();
		hdoc.originalId = id1;
		hdoc.title = name;
		hdoc.fields = new ArrayList<HField>();
		hdoc.fields.add(new HField("LOCATION", location));
		IndexWriter.getInstance().insert(hdoc);
		
		HDocument hdoc2 = new HDocument();
		hdoc2.originalId = id2;
		hdoc2.title = name;
		hdoc2.fields = new ArrayList<HField>();
		hdoc2.fields.add(new HField("LOCATION", location));
		IndexWriter.getInstance().insert(hdoc2);
		
		QueryResult res = IndexReader.getInstance().search(new QueryContext(name));
		assertEquals(2, res.teasers.length);
		System.out.println(res.toString());
	}	
	
	public void testIndexMultidocMultiTimes(String id1,String id2,
			String name, String location) throws Exception {
		testIndexMultiDoc(id1, id2,name, location);
		testIndexMultiDoc(id1, id2,name, location);
		testIndexMultiDoc(id1, id2,name, location);
	}
	
	public void testIndexFieldInsert(String id, String title) throws Exception {
		
		HDocument hdoc = new HDocument();
		hdoc.originalId = id;
		hdoc.title = title;
		hdoc.fields = new ArrayList<HField>();
		HField fld = new HField("BODY",FileReaderUtil.toString("sample.txt"));
		hdoc.fields.add(fld);
		IndexWriter.getInstance().insert(hdoc);
		
		QueryResult res = IndexReader.getInstance().search(
			new QueryContext("Comparable")); //A word from sample.txt
		System.out.println(res.toString());
	}
	
	public void testIndexDelete() throws Exception{
		HDocument hdoc = new HDocument();
		hdoc.originalId = "BIZOSYS-103";
		hdoc.title = "Ram tere Ganga maili";
		hdoc.fields = new ArrayList<HField>();
		
		QueryContext ctx1 = new QueryContext("Ganga");
		IndexWriter.getInstance().insert(hdoc);
		QueryResult res = IndexReader.getInstance().search(ctx1);
		System.out.println("Result:" + res.toString());
		
		QueryContext ctx2 = new QueryContext("Ganga");
		IndexWriter.getInstance().delete("BIZOSYS-103");

		try {
			res = IndexReader.getInstance().search(ctx2);
			System.out.println("Result:" + res.toString());
		} catch (ApplicationFault ex) {
			System.out.println("Result:" + ex.toString());
		}
		
	}	
	
	public void testIndexUpdate(String keyword1, String keyword2, String keyword3, 
			String keyword4, String keyword5, String keyword6, String keyword7,  
			String keyword8, String keyword9, String keyword10) throws Exception {
		
		String[] keywords = new String[] {
				keyword1, keyword2, keyword3, keyword4, keyword5,
				keyword6, keyword7, keyword8, keyword9, keyword10
		};

		StringBuilder sb = new StringBuilder();
		List<HDocument> hdocs = new ArrayList<HDocument>(5000); 
		for ( int i=0; i<5000; i++) {
			HDocument hdoc = new HDocument();
			hdoc.originalId = "ORIG_ID:" + i;
			hdoc.title = "TITLE:" + i;
			sb.delete(0,sb.capacity());
			for (String k : keywords) {
				sb.append(k).append(i).append(' ');		
			} 
			hdoc.fields = new ArrayList<HField>();
			HField fld = new HField("FLD1",sb.toString());
			hdoc.fields.add(fld);
			hdocs.add(hdoc);
		}
		IndexWriter.getInstance().insert(hdocs);
		
		for ( int i=0; i<5000; i++) {
			IndexWriter.getInstance().delete("ORIG_ID:" + i);
		}
		
	}
	

}
