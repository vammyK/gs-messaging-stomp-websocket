package org.siemens.mindverse.model;

import java.util.Date;

import lombok.Data;
@Data
public class TenantAsset {
	
	Long id;
	Long tenantId;
	Long assetId;
	String requestedBy;
	String approvedBy;
	Date requestedOn;
	Date approvedOn;
	String requestNotes;
	String approvedNotes;

}
