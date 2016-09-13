package com.fuzzystream.fif_core;

import java.awt.image.FilteredImageSource;

import com.fuzzystream.exceptions.*;;



public class DescriptionBasedFilter extends Filter {
	
	private Metadata metadata;


	public DescriptionBasedFilter(Metadata metadata) {
		this.metadata = metadata;
	}

	
	/**
	 * This method provides the real implementation of filtering.<br><br>
	 * 
	 * Preconditions: <br><br>
	 * 1-The resource is not equal to null.<br>
	 * 2-The value is between 0 and 1.<br><br>
	 * 3-Must exists an descriptor (associated to the resource in the ResourceRegister class) containing a metadata with the same attribute of the description based filter.
	 * 4-The metadata of the filter and the metadata (with the same attribute) of the descriptor associated to the resource must have the same interpretation.
	 * 
	 * Postconditions:<br><br>
	 * 1-The filtering is done correctly returning a value between 0 and 1.<br><br>
	 * 
	 * @param r The resource you want to filter.
	 * @param value The starting initial filtering value.
	 * 
	 * @throws InterpretationNotEqualException when trying to matching two metadata with different interpretation.
	 * @throws DescriptorWithNoValidMetadataException when a descriptor hasn't a metadata with the specified attribute.
	 */

	@Override
	public double doFilter(Resource r, double value) throws InterpretationNotEqualException, DescriptorWithNoValidMetadataException {
		
		assert (r!=null) : "Description Based Filter: null resource";
		assert (value>=0 && value<=1): "Description Based Filter: invalid input value";
		
		double finalValue = 0;
		
		ResourceRegister rr = ResourceRegister.getinstance();
		Descriptor descriptor = rr.getDescriptor(r);
		
		String attributeName = metadata.getAttribute().getAttributeName();
		Metadata resourceMetadata = descriptor.getMetadata(attributeName);
		
		if(resourceMetadata != null)
			if(resourceMetadata.getInterpretation().equals(metadata.getInterpretation()))
					finalValue = metadata.getInterpretation().match(metadata, resourceMetadata);
			else
				throw new InterpretationNotEqualException();
		else
			return 0;
		
		return FilterUtility.min(finalValue,value);

	}
	
	
	public Metadata getMetadata() {
		return metadata;
	}


	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

}
