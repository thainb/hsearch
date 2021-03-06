package com.bizosys.hsearch.inpipe;

import java.util.List;
import java.util.Vector;

import com.bizosys.hsearch.common.IStorable;
import com.bizosys.hsearch.common.Record;
import com.bizosys.hsearch.hbase.NV;
import com.bizosys.hsearch.index.TermColumns;
import com.bizosys.hsearch.index.TermFamilies;
import com.bizosys.hsearch.index.TermList;

/**
 * This is a Row from Index table which has the 
 * ability to merge and store with previous values.
 * @author karan
 *
 */
public class SaveToIndexRecord extends Record {
	
	TermFamilies termFamilies;
	
	public SaveToIndexRecord(IStorable pk) {
		super(pk);
	}
	
	public SaveToIndexRecord(IStorable pk, List<NV> kvs ) {
		super(pk, kvs);
	}
	
	public SaveToIndexRecord(IStorable pk, NV kv ) {
		super(pk, kv);
	}
	
	public void setTermFamilies(TermFamilies termFamilies) {
		this.termFamilies = termFamilies;
	}
	
	@Override
	public List<NV> getNVs() {
		List<NV> nvs = new Vector<NV>(200);
		termFamilies.toNVs(nvs);
		return nvs;
	}	
	
	@Override
	public boolean merge(byte[] fam, byte[] name, byte[] existingB) {
		char family = (char) fam[0];
		if ( ! termFamilies.families.containsKey(family)) return true;
		TermColumns cols = termFamilies.families.get(family);

		char col = (char) name[0];
		if ( ! cols.columns.containsKey(col)) return true;
		TermList terms = cols.columns.get(col);
		terms.setExistingBytes(existingB);
		return true;
	}	
}
