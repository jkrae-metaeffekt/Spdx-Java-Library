/**
 * Copyright (c) 2019 Source Auditor Inc.
 *
 * SPDX-License-Identifier: Apache-2.0
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.spdx.storage;

import java.util.List;

import org.spdx.library.InvalidSPDXAnalysisException;

/**
 * Interface for storing and retrieving SPDX properties for SPDX documents.
 * 
 * The interface uses the SPDX document URI and an ID to identify specific objects stored.
 * 
 * Each object can have property values and property value lists associated with them.  
 * 
 * A property value is an object of a primitive type (e.g. String or Boolean) or is another
 * object which includes it's own ID and must also have a type described in the SPDX model.
 * 
 * A property list is just a list of values.
 * 
 * @author Gary O'Neall
 *
 */
public interface IModelStore {
	
	/**
	 * Different types of ID's
	 */
	public enum IdType {
		LicenseRef, 		// ID's that start with LicenseRef-
		DocumentRef, 		// ID's that start with DocumentRef-
		SpdxId, 			// ID's that start with SpdxRef-
		ListedLicense, 		// ID's associated with listed licenses
		Literal,			// ID's for pre-defined literals (such as NONE, NOASSERTION)
		Anonomous};			// ID's for object only referenced internally

	/**
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @return true if the id already exists for the document
	 */
	public boolean exists(String documentUri, String id);

	/**
	 * Create a new object with ID
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param type SPDX model type as defined in the CLASS constants in SpdxConstants
	 * @throws InvalidSPDXAnalysisException 
	 */
	public void create(String documentUri, String id, String type) throws InvalidSPDXAnalysisException;

	/**
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @return Property names for all properties having a value for a given id within a document
	 * @throws InvalidSPDXAnalysisException 
	 */
	public List<String> getPropertyValueNames(String documentUri, String id) throws InvalidSPDXAnalysisException;

	/**
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @return Property names for all properties have a value list for a given id within a document
	 * @throws InvalidSPDXAnalysisException 
	 */
	public List<String> getPropertyValueListNames(String documentUri, String id) throws InvalidSPDXAnalysisException;

	/**
	 * Sets a property value for a String or Boolean type of value creating the propertyName if it does not exist
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @param value value to set
	 * @throws InvalidSPDXAnalysisException 
	 */
	public void setValue(String documentUri, String id, String propertyName, Object value) throws InvalidSPDXAnalysisException;

	/**
	 * Sets the value list for the property to an empty list creating the propertyName if it does not exist
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @throws InvalidSPDXAnalysisException 
	 */
	void clearPropertyValueList(String documentUri, String id, String propertyName) throws InvalidSPDXAnalysisException;

	/**
	 * Adds a value to a property list for a String, Boolean or TypedValue type of value creating the propertyName if it does not exist
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @param value value to set
	 * @throws InvalidSPDXAnalysisException 
	 */
	public void addValueToList(String documentUri, String id, String propertyName, Object value) throws InvalidSPDXAnalysisException;

	/**
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @return List of values associated with the id, propertyName and document
	 * @throws InvalidSPDXAnalysisException 
	 */
	public List<?> getValueList(String documentUri, String id, String propertyName) throws InvalidSPDXAnalysisException;

	/**
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @return the single value associated with the id, propertyName and document
	 * @throws InvalidSPDXAnalysisException 
	 */
	public Object getValue(String documentUri, String id, String propertyName) throws InvalidSPDXAnalysisException;

	/**
	 * Generate a unique ID for use within the document
	 * @param idType Type of ID
	 * @param documentUri the SPDX Document URI
	 * @return next available unique ID for the specific idType
	 * @throws InvalidSPDXAnalysisException 
	 */
	public String getNextId(IdType idType, String documentUri) throws InvalidSPDXAnalysisException;
	
	/**
	 * Removes a property from the document for the given ID if the property exists.  Does not raise any exception if the propertyName does not exist
	 * @param documentUri the SPDX Document URI
	 * @param id unique ID within the SPDX document
	 * @param propertyName Name of the property
	 * @throws InvalidSPDXAnalysisException
	 */
	public void removeProperty(String documentUri, String id, String propertyName) throws InvalidSPDXAnalysisException;

	/**
	 * Copy an object from a different Object store creating if it doesn't exist and copying all parameters
	 * @param documentUri document URI
	 * @param id unique ID within the SPDX document
	 * @param type The class name for this object.  Class names are defined in the constants file
	 * @throws InvalidSPDXAnalysisException 
	 */
	public void copyFrom(String documentUri, String id, String type, IModelStore store) throws InvalidSPDXAnalysisException;
}