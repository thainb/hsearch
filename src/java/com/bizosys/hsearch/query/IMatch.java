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
package com.bizosys.hsearch.query;

import com.bizosys.hsearch.common.Storable;
import com.bizosys.hsearch.common.StorableList;

public interface IMatch {
	
	public final static int ENDS_WITH = 1;
	public final static int EQUAL_TO = 2;
	public final static int GREATER_THAN = 3;
	public final static int GREATER_THAN_EQUALTO = 4;
	public final static int LESS_THAN = 5;
	public final static int LESS_THAN_EQUALTO = 6;
	public final static int PATTERN_MATCH = 7;
	public final static int RANGE = 8;
	public final static int STARTS_WITH = 9;
	public final static int WITH_IN = 10;
	
	public boolean match (Storable termValue, Storable termType, 
			Storable foundValue, Storable foundType);
	public boolean match (Storable termValue, StorableList termType,
			Storable foundValue, Storable foundType);
}
