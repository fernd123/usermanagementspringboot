/**
 * Copyright (C) 2019, BuyPower SL
 * All rights reserved.
 */
package es.masingenieros.infinisense.lib;

/**
 * Auditable interface
 * 
 * @author Fernando Rodriguez
 *
 */
public interface Auditable {
	public long getCreatedDate();

	public void setCreatedDate(long createdDate);

	public long getModifiedDate();

	public void setModifiedDate(long modifiedDate);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public String getModifiedBy();

	public void setModifiedBy(String modifiedBy);
}
