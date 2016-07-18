/*
 *  * Copyright 2016 Skymind, Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 */

package org.datavec.api.transform.metadata;

import org.datavec.api.transform.ColumnType;
import org.datavec.api.writable.Writable;

/**
 * Metadata for an String column
 *
 * @author Alex Black
 */
public class StringMetaData extends BaseColumnMetaData {

    //regex + min/max length are nullable: null -> no restrictions on these
    private final String regex;
    private final Integer minLength;
    private final Integer maxLength;

    /**
     * Default constructor with no restrictions on allowable strings
     */
    public StringMetaData(String name) {
        this(name, null, null, null);
    }

    /**
     * @param mustMatchRegex Nullable. If not null: this is a regex that each string must match in order for the entry
     *                       to be considered valid.
     * @param minLength      Min allowable String length. If null: no restriction on min String length
     * @param maxLength      Max allowable String length. If null: no restriction on max String length
     */
    public StringMetaData(String name, String mustMatchRegex, Integer minLength, Integer maxLength) {
        super(name);
        this.regex = mustMatchRegex;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }


    @Override
    public ColumnType getColumnType() {
        return ColumnType.String;
    }

    @Override
    public boolean isValid(Writable writable) {
        String str = writable.toString();
        int len = str.length();
        if (minLength != null && len < minLength) return false;
        if (maxLength != null && len > maxLength) return false;

        return regex == null || str.matches(regex);
    }

    @Override
    public StringMetaData clone() {
        return new StringMetaData(name, regex, minLength, maxLength);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StringMetaData(");
        if (minLength != null) sb.append("minLengthAllowed=").append(minLength);
        if (maxLength != null) {
            if (minLength != null) sb.append(",");
            sb.append("maxLengthAllowed=").append(maxLength);
        }
        if (regex != null) {
            if (minLength != null || maxLength != null) sb.append(",");
            sb.append("regex=").append(regex);
        }
        sb.append(")");
        return sb.toString();
    }

}
